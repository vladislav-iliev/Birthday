package com.vladislaviliev.birthday.kids

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

class AgeCalculator {

    fun timezone(): ZoneId = ZoneId.systemDefault()

    fun getCurrentDateTime(): LocalDateTime = LocalDateTime.now()

    fun calculateMonthsBetween(dob: LocalDateTime, now: LocalDateTime) = ChronoUnit.MONTHS.between(dob, now).toInt()

    fun monthsToYears(months: Int): Int = months / 12
}