package com.vladislaviliev.birthday.networking

import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.kid.Age
import com.vladislaviliev.birthday.kid.text.Text
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LocalApiTest {

    @Test
    fun `initial state should be disconnected`() = runTest {
        Assert.assertEquals(NetworkState.Disconnected(), LocalApi().networkState.first())
    }

    @Test
    fun `states progress up and down`() = runTest {
        val kidsApi = LocalApi()
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