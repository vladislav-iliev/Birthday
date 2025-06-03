package com.vladislaviliev.birthday.kid.text

import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.kid.Age
import com.vladislaviliev.birthday.test.DummyNetworkingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryImplTest {

    @Test
    fun `should follow api state changes`() = runTest {
        val networkingRepo = DummyNetworkingRepository()
        val repo = RepositoryImpl(backgroundScope, coroutineContext[CoroutineDispatcher]!!, networkingRepo)
        runCurrent()

        Assert.assertNull(repo.text.value)

        networkingRepo.connect("", 0)
        runCurrent()
        Assert.assertEquals(null, repo.text.value)

        val text = Text("Johny", Age(5, false), Theme.PELICAN)
        networkingRepo.emit(text)
        runCurrent()
        Assert.assertEquals(text, repo.text.value)

        networkingRepo.disconnect()
        runCurrent()
        Assert.assertNull(repo.text.value)
    }
}