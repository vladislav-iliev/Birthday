package com.vladislaviliev.birthday.kid

import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.networking.Message
import com.vladislaviliev.birthday.networking.NetworkState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class KidsRepositoryTest {

    fun TestScope.createRepo(api: KidApi) = KidRepository(this, coroutineContext[CoroutineDispatcher]!!, api)

    @Test
    fun `repository should have disconnected initial state`() = runTest {
        Assert.assertEquals(NetworkState.Disconnected(), createRepo(InMemoryKidApi()).networkState.first())
    }

    @Test
    fun `connect should change state to connected`() = runTest {
        val repository = createRepo(InMemoryKidApi())
        repository.connect("", 0)
        Assert.assertEquals(NetworkState.Connected(), repository.networkState.first())
    }

    @Test
    fun `repository should reflect incoming messages`() = runTest {
        val api = InMemoryKidApi()
        val repository = createRepo(api)

        val message = Message("JohnyDoe", 1, Theme.PELICAN)
        api.emitMessage(message)

        val state = repository.networkState.first()

        println(api.networkState.first())

        Assert.assertEquals(NetworkState.Connected(message), state)
    }
}