package com.vladislaviliev.birthday.kid

import org.junit.Assert
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneId

class AgeCalculatorTest {
    private val calculator = AgeCalculator()

    private fun getMillisForTimeAgo(years: Long = 0, months: Long = 0): Long {
        val dateTime = LocalDateTime.now().minusYears(years).minusMonths(months)
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    @Test
    fun `test age less than one year returns months`() {
        val dobMillis = getMillisForTimeAgo(months = 6)

        val age = calculator.parseDob(dobMillis)

        Assert.assertEquals(6, age.number)
        Assert.assertTrue(age.isMonths)
    }

    @Test
    fun `test age more than one year returns years`() {
        val dobMillis = getMillisForTimeAgo(years = 2)

        val age = calculator.parseDob(dobMillis)

        Assert.assertEquals(2, age.number)
        Assert.assertFalse(age.isMonths)
    }

    @Test
    fun `test exactly one year age returns one year`() {
        val dobMillis = getMillisForTimeAgo(years = 1)

        val age = calculator.parseDob(dobMillis)

        Assert.assertEquals(1, age.number)
        Assert.assertFalse(age.isMonths)
    }

    @Test
    fun `test newborn age returns zero months`() {
        val dobMillis = getMillisForTimeAgo()

        val age = calculator.parseDob(dobMillis)

        Assert.assertEquals(0, age.number)
        Assert.assertTrue(age.isMonths)
    }
}