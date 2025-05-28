package com.vladislaviliev.birthday.kids

import org.junit.Assert
import org.junit.Test
import java.time.LocalDateTime
import java.time.Month

class AgeCalculatorTest {

    private val calculator = AgeCalculator()

    private fun formatMonthsElapsedErrorMessage(dob: LocalDateTime, now: LocalDateTime) =
        "For DOB: $dob and Now: $now"

    @Test
    fun `test monthsToYears conversion`() {
        Assert.assertEquals(0, calculator.monthsToYears(11))
        Assert.assertEquals(1, calculator.monthsToYears(12))
        Assert.assertEquals(1, calculator.monthsToYears(23))
        Assert.assertEquals(2, calculator.monthsToYears(24))
    }

    @Test
    fun `monthsElapsed basic month calculation`() {
        val dob = LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0)
        val now = LocalDateTime.of(2024, Month.FEBRUARY, 1, 0, 0)
        val result = calculator.calculateMonthsBetween(dob, now)
        Assert.assertEquals(formatMonthsElapsedErrorMessage(dob, now), 1, result)
    }

    @Test
    fun `monthsElapsed one year difference`() {
        val dob = LocalDateTime.of(2023, Month.JANUARY, 1, 0, 0)
        val now = LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0)
        val result = calculator.calculateMonthsBetween(dob, now)
        Assert.assertEquals(formatMonthsElapsedErrorMessage(dob, now), 12, result)
    }

    @Test
    fun `monthsElapsed leap year to leap year February 29`() {
        val dob = LocalDateTime.of(2020, Month.FEBRUARY, 29, 0, 0)
        val now = LocalDateTime.of(2024, Month.FEBRUARY, 29, 0, 0)
        val result = calculator.calculateMonthsBetween(dob, now)
        Assert.assertEquals(formatMonthsElapsedErrorMessage(dob, now), 48, result)
    }

    @Test
    fun `monthsElapsed leap year February 29 to non-leap year February 28`() {
        val dob = LocalDateTime.of(2020, Month.FEBRUARY, 29, 0, 0)
        val now = LocalDateTime.of(2021, Month.FEBRUARY, 28, 0, 0)
        val result = calculator.calculateMonthsBetween(dob, now)
        Assert.assertEquals(formatMonthsElapsedErrorMessage(dob, now), 11, result)
    }

    @Test
    fun `monthsElapsed same instant shows zero months`() {
        val instant = LocalDateTime.of(2024, Month.JANUARY, 1, 12, 30)
        val result = calculator.calculateMonthsBetween(instant, instant)
        Assert.assertEquals(formatMonthsElapsedErrorMessage(instant, instant), 0, result)
    }

    @Test
    fun `monthsElapsed future date shows negative months`() {
        val dob = LocalDateTime.of(2024, Month.MARCH, 1, 0, 0)
        val now = LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0)
        val result = calculator.calculateMonthsBetween(dob, now)
        Assert.assertEquals(formatMonthsElapsedErrorMessage(dob, now), -2, result)
    }

    @Test
    fun `monthsElapsed non-leap year February 28 to leap year February 29`() {
        val dob = LocalDateTime.of(2023, Month.FEBRUARY, 28, 0, 0)
        val now = LocalDateTime.of(2024, Month.FEBRUARY, 29, 0, 0)
        val result = calculator.calculateMonthsBetween(dob, now)
        Assert.assertEquals(formatMonthsElapsedErrorMessage(dob, now), 12, result)
    }

    @Test
    fun `monthsElapsed leap day to next day`() {
        val dob = LocalDateTime.of(2024, Month.FEBRUARY, 29, 0, 0)
        val now = LocalDateTime.of(2024, Month.MARCH, 1, 0, 0)
        val result = calculator.calculateMonthsBetween(dob, now)
        Assert.assertEquals(formatMonthsElapsedErrorMessage(dob, now), 0, result)
    }

    @Test
    fun `monthsElapsed 31 days month to 30 days month`() {
        val dob = LocalDateTime.of(2024, Month.JANUARY, 31, 0, 0)
        val now = LocalDateTime.of(2024, Month.APRIL, 30, 0, 0)
        val result = calculator.calculateMonthsBetween(dob, now)
        Assert.assertEquals(formatMonthsElapsedErrorMessage(dob, now), 2, result)
    }

    @Test
    fun `monthsElapsed spanning multiple leap years`() {
        val dob = LocalDateTime.of(2020, Month.FEBRUARY, 29, 0, 0)
        val now = LocalDateTime.of(2024, Month.FEBRUARY, 29, 0, 0)
        val result = calculator.calculateMonthsBetween(dob, now)
        Assert.assertEquals(formatMonthsElapsedErrorMessage(dob, now), 48, result)
    }

    @Test
    fun `monthsElapsed year transition non-leap to leap with time`() {
        val dob = LocalDateTime.of(2023, Month.DECEMBER, 31, 23, 59)
        val now = LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0)
        val result = calculator.calculateMonthsBetween(dob, now)
        Assert.assertEquals(formatMonthsElapsedErrorMessage(dob, now), 0, result)
    }

    @Test
    fun `monthsElapsed last day of month transitions`() {
        val dob = LocalDateTime.of(2024, Month.JANUARY, 31, 0, 0)
        val now = LocalDateTime.of(2024, Month.FEBRUARY, 29, 0, 0)
        val result = calculator.calculateMonthsBetween(dob, now)
        Assert.assertEquals(formatMonthsElapsedErrorMessage(dob, now), 0, result)
    }

    @Test
    fun `monthsElapsed February 28 to March 1 in leap year`() {
        val dob = LocalDateTime.of(2024, Month.FEBRUARY, 28, 0, 0)
        val now = LocalDateTime.of(2024, Month.MARCH, 1, 0, 0)
        val result = calculator.calculateMonthsBetween(dob, now)
        Assert.assertEquals(formatMonthsElapsedErrorMessage(dob, now), 0, result)
    }

    @Test
    fun `monthsElapsed month end to month end spanning leap year`() {
        val dob = LocalDateTime.of(2024, Month.JANUARY, 31, 0, 0)
        val now = LocalDateTime.of(2025, Month.JANUARY, 31, 0, 0)
        val result = calculator.calculateMonthsBetween(dob, now)
        Assert.assertEquals(formatMonthsElapsedErrorMessage(dob, now), 12, result)
    }

    @Test
    fun `monthsElapsed one minute difference at month boundary`() {
        val dob = LocalDateTime.of(2024, Month.JANUARY, 31, 23, 59)
        val now = LocalDateTime.of(2024, Month.FEBRUARY, 1, 0, 0)
        val result = calculator.calculateMonthsBetween(dob, now)
        Assert.assertEquals(formatMonthsElapsedErrorMessage(dob, now), 0, result)
    }

    @Test
    fun `monthsElapsed leap year February to non-leap year February multiple years`() {
        val dob = LocalDateTime.of(2020, Month.FEBRUARY, 29, 0, 0)
        val now = LocalDateTime.of(2023, Month.FEBRUARY, 28, 0, 0)
        val result = calculator.calculateMonthsBetween(dob, now)
        Assert.assertEquals(formatMonthsElapsedErrorMessage(dob, now), 35, result)
    }

    @Test
    fun `parseForDisplay converts 24 months to 2 years`() {
        Assert.assertEquals(AgeToDisplay(2, false), calculator.parseForDisplay(24))
    }

    @Test
    fun `parseForDisplay converts 36 months to 3 years`() {
        Assert.assertEquals(AgeToDisplay(3, false), calculator.parseForDisplay(36))
    }

    @Test
    fun `parseForDisplay converts 48 months to 4 years`() {
        Assert.assertEquals(AgeToDisplay(4, false), calculator.parseForDisplay(48))
    }

    @Test
    fun `parseForDisplay represents newborn as 0 months`() {
        Assert.assertEquals(AgeToDisplay(0, true), calculator.parseForDisplay(0))
    }

    @Test
    fun `parseForDisplay keeps 1 month as months`() {
        Assert.assertEquals(AgeToDisplay(1, true), calculator.parseForDisplay(1))
    }

    @Test
    fun `parseForDisplay keeps 6 months as months`() {
        Assert.assertEquals(AgeToDisplay(6, true), calculator.parseForDisplay(6))
    }

    @Test
    fun `parseForDisplay keeps 11 months as months`() {
        Assert.assertEquals(AgeToDisplay(11, true), calculator.parseForDisplay(11))
    }

    @Test
    fun `parseForDisplay converts exactly 12 months to 1 year`() {
        Assert.assertEquals(AgeToDisplay(1, false), calculator.parseForDisplay(12))
    }

}