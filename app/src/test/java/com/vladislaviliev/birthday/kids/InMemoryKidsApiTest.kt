package com.vladislaviliev.birthday.kids

import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.networking.Response
import com.vladislaviliev.birthday.networking.State
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
        Assert.assertEquals(State.Disconnected(), InMemoryKidsApi().state.first())
    }

    @Test
    fun `states progress up and down`() = runTest {
        val kidsApi = InMemoryKidsApi()
        val states = mutableListOf<State>()

        backgroundScope.launch { kidsApi.state.collect { states.add(it) } }
        runCurrent()
        val response = Response("JohnyDoe", 1, Theme.PELICAN)

        kidsApi.connect("", 0)
        kidsApi.emitResponse(response)
        kidsApi.disconnect()

        Assert.assertEquals(State.Disconnected(), states[0])
        Assert.assertEquals(State.Connecting, states[1])
        Assert.assertEquals(State.Connected(), states[2])
        Assert.assertEquals(State.Connected(response), states[3])
        Assert.assertTrue(states[4] is State.Disconnected)
    }
}