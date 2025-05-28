package com.vladislaviliev.birthday.kids

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

class AgeCalculator {

    val timezone: ZoneId = ZoneId.systemDefault()

    val currentDateTime: LocalDateTime get() = LocalDateTime.now()

    fun calculateMonthsBetween(dob: LocalDateTime, now: LocalDateTime) = ChronoUnit.MONTHS.between(dob, now).toInt()

    fun monthsToYears(months: Int): Int = months / 12

    fun parseForDisplay(ageMonths: Int): AgeToDisplay {
        val years = monthsToYears(ageMonths)
        return if (0 < years) AgeToDisplay(years, false) else AgeToDisplay(ageMonths, true)
    }
}