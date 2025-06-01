package com.vladislaviliev.birthday.kid.text

import kotlinx.coroutines.flow.StateFlow

interface Repository {
    val text: StateFlow<Text?>
}