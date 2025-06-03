package com.vladislaviliev.birthday.test

import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.kid.Age
import com.vladislaviliev.birthday.kid.text.Text
import com.vladislaviliev.birthday.networking.NetworkState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DummyNetworkingRepositoryTest {

    @Test
    fun `states progress up and down`() = runTest {
        val repo = DummyNetworkingRepository()
        val networkStates = mutableListOf<NetworkState>()

        backgroundScope.launch { repo.state.collect { networkStates.add(it) } }
        runCurrent()
        val text = Text("Johny", Age(0, false), Theme.PELICAN)

        repo.connect("", 0)
        repo.emit(text)
        repo.disconnect()

        Assert.assertEquals(NetworkState.Connecting, networkStates[0])
        Assert.assertEquals(NetworkState.Connected(), networkStates[1])
        Assert.assertEquals(NetworkState.Connected(text), networkStates[2])
        Assert.assertTrue(networkStates[3] is NetworkState.Disconnected)
    }
}