package com.vladislaviliev.birthday.networking

import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.kid.AgeCalculator
import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.LocalDateTime

@Serializable
data class NetworkMessageRaw(val name: String, val dob: Long, val theme: String)

data class NetworkMessage(val name: String, val ageMonths: Int, val theme: Theme)

fun NetworkMessageRaw.beautify(): NetworkMessage {
    val calculator = AgeCalculator()
    val timezone = calculator.timezone

    val dob = LocalDateTime.ofInstant(Instant.ofEpochMilli(dob), timezone)
    val now = calculator.currentDateTime
    val ageMonths = calculator.calculateMonthsBetween(dob, now)

    val theme = Theme.entries.first { it.name.equals(theme, true) }
    return NetworkMessage(name, ageMonths, theme)
}