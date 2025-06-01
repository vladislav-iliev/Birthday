package com.vladislaviliev.birthday.networking

import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.kid.AgeCalculator
import com.vladislaviliev.birthday.kid.text.Text
import kotlinx.serialization.Serializable

@Serializable
data class TextRaw(val name: String, val dob: Long, val theme: String)

fun TextRaw.beautify() =
    Text(name, AgeCalculator().parseDob(dob), Theme.entries.first { it.name.equals(theme, true) })