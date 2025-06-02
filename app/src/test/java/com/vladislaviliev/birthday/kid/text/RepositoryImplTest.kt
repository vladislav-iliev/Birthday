package com.vladislaviliev.birthday.kid.text

import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.kid.Age
import com.vladislaviliev.birthday.networking.NetworkState
import com.vladislaviliev.birthday.test.LocalApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.yield
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryImplTest {

    @Test
    fun `should follow api state changes`() = runTest {
        val api = LocalApi()
        val repo = RepositoryImpl(backgroundScope, coroutineContext[CoroutineDispatcher]!!, api)
        runCurrent()

        Assert.assertNull(repo.text.value)

        api.connect("", 0)
        runCurrent()
        Assert.assertEquals(null, repo.text.value)

        val text = Text("Johny", Age(5, false), Theme.PELICAN)
        api.emit(text)
        runCurrent()
        Assert.assertEquals(text, repo.text.value)

        api.disconnect()
        runCurrent()
        Assert.assertNull(repo.text.value)
    }
}