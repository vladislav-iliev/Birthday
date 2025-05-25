package com.vladislaviliev.birthday

import com.vladislaviliev.birthday.kids.InMemoryKidsApi
import com.vladislaviliev.birthday.networking.Response
import com.vladislaviliev.birthday.networking.State
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import org.junit.Assert
import org.junit.Test

class InMemoryKidsApiTest {

    @Test
    fun `initial state should be disconnected`() = runTest {
        Assert.assertEquals(State.Disconnected(), InMemoryKidsApi().state.value)
    }

    @Test
    fun `connect should change state to connected`() = runTest {
        val kidsApi = InMemoryKidsApi()
        kidsApi.connect()
        Assert.assertEquals(State.Connected(), kidsApi.state.value)
    }

    @Test
    fun `multiple connects should not change state if already connected`() = runTest {
        val kidsApi = InMemoryKidsApi()

        kidsApi.connect()
        val firstConnectedState = kidsApi.state.value

        kidsApi.connect()
        val secondConnectedState = kidsApi.state.value

        Assert.assertEquals(firstConnectedState, secondConnectedState)
    }


    @Test
    fun `emitResponse should emit connected state with response`() = runTest {
        val kidsApi = InMemoryKidsApi()
        val toEmit = Response("JohnyDoe", LocalDateTime.parse("2025-05-26T00:19:16"), Theme.PELICAN)

        kidsApi.connect()
        kidsApi.emitResponse(toEmit)

        Assert.assertEquals(State.Connected(toEmit), kidsApi.state.value)
    }
}