package com.vladislaviliev.birthday.networking

import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.kids.AgeCalculator
import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.LocalDateTime

@Serializable
data class ResponseRaw(val name: String, val dob: Long, val theme: String)

data class Response(val name: String, val dob: LocalDateTime, val theme: Theme)

fun ResponseRaw.beautify(): Response {
    val timezone = AgeCalculator().timezone()
    val dob = LocalDateTime.ofInstant(Instant.ofEpochMilli(dob), timezone)
    val theme = Theme.entries.first { it.name.equals(theme, true) }
    return Response(name, dob, theme)
}