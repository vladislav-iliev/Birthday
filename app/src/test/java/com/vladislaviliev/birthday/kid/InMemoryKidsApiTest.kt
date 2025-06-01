package com.vladislaviliev.birthday.kid

import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.kid.text.Text
import com.vladislaviliev.birthday.networking.InMemoryApi
import com.vladislaviliev.birthday.networking.NetworkState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class InMemoryKidsApiTest {

    @Test
    fun `initial state should be disconnected`() = runTest {
        Assert.assertEquals(NetworkState.Disconnected(), InMemoryApi().networkState.first())
    }

    @Test
    fun `states progress up and down`() = runTest {
        val kidsApi = InMemoryApi()
        val networkStates = mutableListOf<NetworkState>()

        backgroundScope.launch { kidsApi.networkState.collect { networkStates.add(it) } }
        runCurrent()
        val text = Text("Johny", Age(0, false), Theme.PELICAN)

        kidsApi.connect("", 0)
        kidsApi.emit(text)
        kidsApi.disconnect()

        Assert.assertEquals(NetworkState.Disconnected(), networkStates[0])
        Assert.assertEquals(NetworkState.Connecting, networkStates[1])
        Assert.assertEquals(NetworkState.Connected(), networkStates[2])
        Assert.assertEquals(NetworkState.Connected(text), networkStates[3])
        Assert.assertTrue(networkStates[4] is NetworkState.Disconnected)
    }
}