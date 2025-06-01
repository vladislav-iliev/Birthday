package com.vladislaviliev.birthday.kid

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

class AgeCalculator {

    fun parseDob(dob: Long): Age {
        val dob = LocalDateTime.ofInstant(Instant.ofEpochMilli(dob), ZoneId.systemDefault())
        val now = LocalDateTime.now()
        val ageMonths = ChronoUnit.MONTHS.between(dob, now).toInt()
        val ageYears = ageMonths / 12
        return if (0 < ageYears) Age(ageYears, false) else Age(ageMonths, true)
    }
}