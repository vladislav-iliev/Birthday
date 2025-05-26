package com.vladislaviliev.birthday

import com.vladislaviliev.birthday.kids.InMemoryKidsApi
import com.vladislaviliev.birthday.kids.KidsRepository
import com.vladislaviliev.birthday.networking.Response
import com.vladislaviliev.birthday.networking.State
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import org.junit.Assert
import org.junit.Test

class KidsRepositoryTest {

    @Test
    fun `repository should have disconnected initial state`() = runTest {
        val repository = KidsRepository(InMemoryKidsApi())
        Assert.assertEquals(State.Disconnected(), repository.state.first())
    }

    @Test
    fun `connect should change state to connected`() = runTest {
        val repository = KidsRepository(InMemoryKidsApi())
        repository.connect()
        Assert.assertEquals(State.Connected(), repository.state.first())
    }

    @Test
    fun `repository should reflect api response state changes`() = runTest {
        val api = InMemoryKidsApi()
        val repository = KidsRepository(api)

        val response = Response("JohnyDoe", LocalDateTime.parse("2025-05-26T00:19:16"), Theme.PELICAN)
        api.emitResponse(response)

        Assert.assertEquals(State.Connected(response), repository.state.first())
    }
}