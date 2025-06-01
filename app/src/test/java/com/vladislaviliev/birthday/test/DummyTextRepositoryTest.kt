package com.vladislaviliev.birthday.test

import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.kid.Age
import com.vladislaviliev.birthday.kid.text.Text
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DummyTextRepositoryTest {

    @Test
    fun `initial text value should be null`() = runTest {
        val repository = DummyTextRepository()
        val initialValue = repository.text.first()
        assertNull(initialValue)
    }

    @Test
    fun `emit should update text value`() = runTest {
        val repository = DummyTextRepository()
        val testText = Text(
            name = "Test Name",
            age = Age(number = 5, isMonths = false),
            theme = Theme.FOX
        )

        repository.emit(testText)

        val result = repository.text.first()
        assertEquals(testText, result)
    }

    @Test
    fun `emit null should clear text value`() = runTest {
        val repository = DummyTextRepository()
        val testText = Text(
            name = "Test Name",
            age = Age(number = 5, isMonths = false),
            theme = Theme.ELEPHANT
        )
        repository.emit(testText)

        repository.emit(null)

        val result = repository.text.first()
        assertNull(result)
    }

    @Test
    fun `multiple emits should update to latest value`() = runTest {
        val repository = DummyTextRepository()
        val firstText = Text(
            name = "First",
            age = Age(number = 3, isMonths = true),
            theme = Theme.PELICAN
        )
        val secondText = Text(
            name = "Second",
            age = Age(number = 7, isMonths = false),
            theme = Theme.FOX
        )

        repository.emit(firstText)
        repository.emit(secondText)

        val result = repository.text.first()
        assertEquals(secondText, result)
    }
}