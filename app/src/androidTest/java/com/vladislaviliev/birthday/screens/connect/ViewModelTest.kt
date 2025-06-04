package com.vladislaviliev.birthday.screens.connect

import androidx.lifecycle.viewModelScope
import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.kid.Age
import com.vladislaviliev.birthday.kid.text.Text
import com.vladislaviliev.birthday.networking.NetworkState
import com.vladislaviliev.birthday.test.DummyAvatarRepository
import com.vladislaviliev.birthday.test.DummyNetworkingRepository
import com.vladislaviliev.birthday.test.DummyTextRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelTest {

    private fun TestScope.createViewModelAndApi(): Pair<ViewModel, DummyNetworkingRepository> {
        val repo = DummyNetworkingRepository()
        val viewModel = ViewModel(repo, DummyTextRepository(), DummyAvatarRepository())
        return Pair(viewModel, repo)
    }

    @Test
    fun initial_state_should_be_disconnected() = runTest {
        val (viewModel, _) = createViewModelAndApi()
        Assert.assertEquals(NetworkState.Disconnected(), viewModel.networkState.value)
    }

    @Test
    fun connection_state_reflects_api_state() = runTest {

        val (viewModel, networkRepo) = createViewModelAndApi()
        val collected = mutableListOf<NetworkState>()
        backgroundScope.launch { viewModel.networkState.onEach { println("HAHA $it") }.toList(collected) }
        runCurrent()

        viewModel.connect("", 0)
        val text = Text("Johny", Age(0, false), Theme.PELICAN)

        launch(Dispatchers.Main) {
            networkRepo.emit(text)
            networkRepo.disconnect()
        }.join()

        println(collected)
        Assert.assertEquals(1, collected.count { it == NetworkState.Connected() })
        Assert.assertEquals(1, collected.count { it == NetworkState.Connected(text) })
        Assert.assertEquals(2, collected.count { it is NetworkState.Disconnected })
    }

    @Test
    fun multiple_connect_calls_should_not_affect_final_state() = runTest {
        val (viewModel, networkRepo) = createViewModelAndApi()
        val collected = mutableListOf<NetworkState>()
        backgroundScope.launch { viewModel.networkState.toList(collected) }

        runCurrent()
        repeat(3) { viewModel.connect("", 0) }
        launch(Dispatchers.Main) { networkRepo.disconnect() }.join()

        Assert.assertTrue(viewModel.networkState.value is NetworkState.Disconnected)
        Assert.assertEquals(collected.count { it is NetworkState.Connecting }, 1)
        Assert.assertEquals(collected.count { it is NetworkState.Connected }, 1)
    }
}