package com.vladislaviliev.birthday.screens.kid

import com.vladislaviliev.birthday.MainDispatcherRule
import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.kids.InMemoryKidsApi
import com.vladislaviliev.birthday.networking.Message
import com.vladislaviliev.birthday.networking.State
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

    private fun TestScope.createViewModelAndApi(): Pair<ViewModel, InMemoryKidsApi> {
        val api = InMemoryKidsApi()
        val viewModel = ViewModel(api)
        return Pair(viewModel, api)
    }

    @Test
    fun `initial state should match api initial state`() = runTest {
        val (viewModel, _) = createViewModelAndApi()
        Assert.assertEquals(State.Disconnected(), viewModel.state.value)
    }

    @Test
    fun `viewmodel state should reflect api state changes`() = runTest {
        val (viewModel, api) = createViewModelAndApi()
        backgroundScope.launch { viewModel.state.collect { } }
        runCurrent()

        // When API connects
        api.connect("", 0)
        Assert.assertEquals(State.Connected(), viewModel.state.value)

        // When API receives message
        val message = Message("JohnyDoe", 1, Theme.PELICAN)
        api.emitMessage(message)
        Assert.assertEquals(State.Connected(message), viewModel.state.value)

        // When API disconnects
        api.disconnect()
        Assert.assertEquals(State.Disconnected(), viewModel.state.value)
    }
}