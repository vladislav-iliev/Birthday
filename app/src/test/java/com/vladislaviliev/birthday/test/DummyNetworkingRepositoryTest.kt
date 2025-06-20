package com.vladislaviliev.birthday.test

import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.kid.Age
import com.vladislaviliev.birthday.kid.text.Text
import com.vladislaviliev.birthday.networking.NetworkState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DummyNetworkingRepositoryTest {

    @Test
    fun `states progress up and down`() = runTest(UnconfinedTestDispatcher()) {
        val repo = DummyNetworkingRepository()
        val networkStates = mutableListOf<NetworkState>()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { repo.state.toList(networkStates) }
        val text = Text("Johny", Age(0, false), Theme.PELICAN)

        repo.connect("", 0)
        repo.emit(text)
        repo.disconnect()

        Assert.assertEquals(
            listOf(
                NetworkState.Disconnected(),
                NetworkState.Connecting,
                NetworkState.Connected(),
                NetworkState.Connected(text),
                NetworkState.Disconnected()
            ),
            networkStates
        )
    }
}