package com.vladislaviliev.birthday.test

import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.kid.Age
import com.vladislaviliev.birthday.kid.text.Text
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DummyTextRepositoryTest {

    @Test
    fun `initial text value should be null`() = runTest {
        assertNull(DummyTextRepository().text.first())
    }

    @Test
    fun `emit should update text value`() = runTest {
        val repository = DummyTextRepository()
        val testText = Text("Test Name", Age(5, false), Theme.FOX)

        repository.emit(testText)
        assertEquals(testText, repository.text.first())
    }

    @Test
    fun `emit null should clear text value`() = runTest {
        val repository = DummyTextRepository()
        val testText = Text("Test Name", Age(5, false), Theme.ELEPHANT)
        repository.emit(testText)
        repository.emit(null)
        assertNull(repository.text.first())
    }

    @Test
    fun `multiple emits should update to latest value`() = runTest {
        val repository = DummyTextRepository()
        val firstText = Text("First", Age(3, true), Theme.PELICAN)
        val secondText = Text("Second", Age(7, false), Theme.FOX)

        repository.emit(firstText)
        repository.emit(secondText)
        assertEquals(secondText, repository.text.first())
    }
}