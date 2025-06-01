package com.vladislaviliev.birthday.screens.connect

import com.vladislaviliev.birthday.MainDispatcherRule
import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.kid.Age
import com.vladislaviliev.birthday.networking.InMemoryApi
import com.vladislaviliev.birthday.kid.KidRepository
import com.vladislaviliev.birthday.kid.text.Text
import com.vladislaviliev.birthday.networking.NetworkState
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

    private fun TestScope.createViewModelAndApi(): Pair<ViewModel, InMemoryApi> {
        val api = InMemoryApi()
        val repository = KidRepository(this, coroutineContext[CoroutineDispatcher]!!, api)
        val viewModel = ViewModel(repository)
        return Pair(viewModel, api)
    }

    @Test
    fun `initial state should be disconnected`() = runTest {
        val (viewModel, _) = createViewModelAndApi()
        Assert.assertEquals(NetworkState.Disconnected(), viewModel.networkState.value)
    }

    @Test
    fun `connection state reflects api state`() = runTest {

        val (viewModel, api) = createViewModelAndApi()
        backgroundScope.launch { viewModel.networkState.collect { } }
        viewModel.connect("", 0)
        runCurrent()

        Assert.assertEquals(NetworkState.Connected(), viewModel.networkState.value)
        val text = Text("Johny", Age(0, false), Theme.PELICAN)
        api.emit(text)
        Assert.assertEquals(NetworkState.Connected(text), viewModel.networkState.value)
        api.disconnect()
        Assert.assertEquals(NetworkState.Disconnected(), viewModel.networkState.value)
    }

    @Test
    fun `multiple connect calls should not affect final state`() = runTest {
        val (viewModel, _) = createViewModelAndApi()
        backgroundScope.launch { viewModel.networkState.collect { } }
        repeat(3) { viewModel.connect("", 0) }
        runCurrent()
        Assert.assertEquals(NetworkState.Connected(), viewModel.networkState.value)
    }
}