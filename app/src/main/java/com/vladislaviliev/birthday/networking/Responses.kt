package com.vladislaviliev.birthday.networking

import com.vladislaviliev.birthday.Theme
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class ResponseRaw(val name: String, val dob: Long, val theme: String)

data class Response(val name: String, val dob: LocalDateTime, val theme: Theme)

fun ResponseRaw.beautify(): Response {
    val dob = Instant.fromEpochMilliseconds(dob).toLocalDateTime(TimeZone.currentSystemDefault())
    val theme = Theme.entries.first { it.name.equals(theme, true) }
    return Response(name, dob, theme)
}