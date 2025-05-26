package com.vladislaviliev.birthday.screens.connect

import com.vladislaviliev.birthday.MainDispatcherRule
import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.kids.InMemoryKidsApi
import com.vladislaviliev.birthday.kids.KidsRepository
import com.vladislaviliev.birthday.networking.Response
import com.vladislaviliev.birthday.networking.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private fun TestScope.createViewModelAndApi(): Pair<ViewModel, InMemoryKidsApi> {
        val api = InMemoryKidsApi()
        val repository = KidsRepository(this, coroutineContext[CoroutineDispatcher]!!, api)
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
        val response = Response("JohnyDoe", LocalDateTime.parse("2025-05-26T00:19:16"), Theme.PELICAN)
        api.emitResponse(response)
        Assert.assertEquals(State.Connected(response), viewModel.state.value)
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