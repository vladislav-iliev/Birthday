package com.vladislaviliev.birthday.screens.kid

import com.vladislaviliev.birthday.MainDispatcherRule
import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.dependencies.DummyAvatarRepository
import com.vladislaviliev.birthday.kid.Age
import com.vladislaviliev.birthday.kid.State
import com.vladislaviliev.birthday.kid.StateTransformer
import com.vladislaviliev.birthday.networking.InMemoryApi
import com.vladislaviliev.birthday.kid.avatar.Repository
import com.vladislaviliev.birthday.kid.text.Text
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

    private fun TestScope.createViewModelAndApi(): Triple<ViewModel, InMemoryApi, Repository> {
        val api = InMemoryApi()
        val avatarRepo = DummyAvatarRepository()
        val viewModel = ViewModel(api, DummyAvatarRepository())
        return Triple(viewModel, api, avatarRepo)
    }

    @Test
    fun `initial state should match api initial state`() = runTest {
        val (viewModel, api, avatarRepo) = createViewModelAndApi()
        Assert.assertEquals(
            StateTransformer().from(api.networkState.value, avatarRepo.bitmap.value),
            viewModel.state.value
        )
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
        val networkMessage = Text("JohnyDoe", Age(-1, false), Theme.PELICAN)
        api.emit(networkMessage)
        Assert.assertEquals(
            State(true, Text("JohnyDoe", Age(-1, false), Theme.PELICAN), null), viewModel.state.value
        )

        // When API disconnects
        api.disconnect()
        Assert.assertFalse(viewModel.state.value.isActive)
    }
}