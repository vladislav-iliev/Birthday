package com.vladislaviliev.birthday

import com.vladislaviliev.birthday.kids.InMemoryKidsApi
import com.vladislaviliev.birthday.kids.KidsApi
import com.vladislaviliev.birthday.kids.KidsRepository
import com.vladislaviliev.birthday.networking.Response
import com.vladislaviliev.birthday.networking.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class KidsRepositoryTest {

    fun TestScope.createRepo(api: KidsApi) = KidsRepository(this, coroutineContext[CoroutineDispatcher]!!, api)

    @Test
    fun `repository should have disconnected initial state`() = runTest {
        Assert.assertEquals(State.Disconnected(), createRepo(InMemoryKidsApi()).state.first())
    }

    @Test
    fun `connect should change state to connected`() = runTest {
        val repository = createRepo(InMemoryKidsApi())
        repository.connect()
        Assert.assertEquals(State.Connected(), repository.state.first())
    }

    @Test
    fun `repository should reflect api response state changes`() = runTest {
        val api = InMemoryKidsApi()
        val repository = createRepo(api)

        val response = Response("JohnyDoe", LocalDateTime.parse("2025-05-26T00:19:16"), Theme.PELICAN)
        api.emitResponse(response)

        val state = repository.state.first()

        println(api.state.first())

        Assert.assertEquals(State.Connected(response), state)
    }
}