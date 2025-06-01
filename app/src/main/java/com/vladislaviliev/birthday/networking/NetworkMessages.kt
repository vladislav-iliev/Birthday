package com.vladislaviliev.birthday.networking

import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.kid.Age
import com.vladislaviliev.birthday.kid.AgeCalculator
import kotlinx.serialization.Serializable

@Serializable
data class NetworkMessageRaw(val name: String, val dob: Long, val theme: String)

data class NetworkMessage(val name: String, val age: Age, val theme: Theme)

fun NetworkMessageRaw.beautify() =
    NetworkMessage(name, AgeCalculator().parseDob(dob), Theme.entries.first { it.name.equals(theme, true) })