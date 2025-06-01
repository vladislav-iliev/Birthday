package com.vladislaviliev.birthday.kid

import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.kid.text.Text
import com.vladislaviliev.birthday.networking.Api
import com.vladislaviliev.birthday.networking.LocalApi
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

    fun TestScope.createRepo(api: Api) = KidRepository(this, coroutineContext[CoroutineDispatcher]!!, api)

    @Test
    fun `repository should have disconnected initial state`() = runTest {
        Assert.assertEquals(NetworkState.Disconnected(), createRepo(LocalApi()).networkState.first())
    }

    @Test
    fun `connect should change state to connected`() = runTest {
        val repository = createRepo(LocalApi())
        repository.connect("", 0)
        Assert.assertEquals(NetworkState.Connected(), repository.networkState.first())
    }

    @Test
    fun `repository should reflect incoming messages`() = runTest {
        val api = LocalApi()
        val repository = createRepo(api)

        val text = Text("Johny", Age(0, false), Theme.PELICAN)
        api.emit(text)

        val state = repository.networkState.first()

        println(api.networkState.first())

        Assert.assertEquals(NetworkState.Connected(text), state)
    }
}