package com.vladislaviliev.birthday.screens.connect

import com.vladislaviliev.birthday.MainDispatcherRule
import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.kid.InMemoryKidApi
import com.vladislaviliev.birthday.kid.KidRepository
import com.vladislaviliev.birthday.networking.Message
import com.vladislaviliev.birthday.networking.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private fun TestScope.createViewModelAndApi(): Pair<ViewModel, InMemoryKidApi> {
        val api = InMemoryKidApi()
        val repository = KidRepository(this, coroutineContext[CoroutineDispatcher]!!, api)
        val viewModel = ViewModel(repository)
        return Pair(viewModel, api)
    }

    @Test
    fun `initial state should be disconnected`() = runTest {
        val (viewModel, _) = createViewModelAndApi()
        Assert.assertEquals(State.Disconnected(), viewModel.state.value)
    }

    @Test
    fun `connection state reflects api state`() = runTest {

        val (viewModel, api) = createViewModelAndApi()
        backgroundScope.launch { viewModel.state.collect { } }
        viewModel.connect("", 0)
        runCurrent()

        Assert.assertEquals(State.Connected(), viewModel.state.value)
        val message = Message("JohnyDoe", 1, Theme.PELICAN)
        api.emitMessage(message)
        Assert.assertEquals(State.Connected(message), viewModel.state.value)
        api.disconnect()
        Assert.assertEquals(State.Disconnected(), viewModel.state.value)
    }

    @Test
    fun `multiple connect calls should not affect final state`() = runTest {
        val (viewModel, _) = createViewModelAndApi()
        backgroundScope.launch { viewModel.state.collect { } }
        repeat(3) { viewModel.connect("", 0) }
        runCurrent()
        Assert.assertEquals(State.Connected(), viewModel.state.value)
    }
}