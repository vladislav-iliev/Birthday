package com.vladislaviliev.birthday.kid.text

import kotlinx.coroutines.flow.StateFlow

interface TextRepository {
    val text: StateFlow<Text?>
}