package com.vladislaviliev.birthday.screens.kid

import androidx.compose.ui.graphics.ImageBitmap
import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.kid.Age

data class KidScreenState(
    val isActive: Boolean,
    val name: String,
    val age: Age,
    val theme: Theme,
    val avatar: ImageBitmap?
)