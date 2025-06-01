package com.vladislaviliev.birthday.screens.kid

import com.vladislaviliev.birthday.MainDispatcherRule
import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.dependencies.DummyAvatarRepository
import com.vladislaviliev.birthday.kid.InMemoryKidApi
import com.vladislaviliev.birthday.networking.NetworkMessage
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
        val viewModel = ViewModel(api, DummyAvatarRepository())
        return Pair(viewModel, api)
    }

    @Test
    fun `initial state should match api initial state`() = runTest {
        val (viewModel, _) = createViewModelAndApi()
        Assert.assertEquals(StateTransformer().defaultState, viewModel.state.value)
    }

    @Test
    fun `viewmodel state should reflect api state changes`() = runTest {
        val (viewModel, api) = createViewModelAndApi()
        backgroundScope.launch { viewModel.state.collect { } }
        runCurrent()

        // When API connects
        api.connect("", 0)
        Assert.assertFalse(viewModel.state.value.isActive)

        // When API receives message
        val networkMessage = NetworkMessage("JohnyDoe", 1, Theme.PELICAN)
        api.emitMessage(networkMessage)
        Assert.assertEquals(KidScreenState(true, "JohnyDoe", 1, Theme.PELICAN, null), viewModel.state.value)

        // When API disconnects
        api.disconnect()
        Assert.assertFalse(viewModel.state.value.isActive)
    }
}