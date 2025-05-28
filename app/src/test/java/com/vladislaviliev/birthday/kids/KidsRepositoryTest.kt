package com.vladislaviliev.birthday.kids

import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.networking.Message
import com.vladislaviliev.birthday.networking.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
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
        repository.connect("", 0)
        Assert.assertEquals(State.Connected(), repository.state.first())
    }

    @Test
    fun `repository should reflect incoming messages`() = runTest {
        val api = InMemoryKidsApi()
        val repository = createRepo(api)

        val message = Message("JohnyDoe", 1, Theme.PELICAN)
        api.emitMessage(message)

        val state = repository.state.first()

        println(api.state.first())

        Assert.assertEquals(State.Connected(message), state)
    }
}