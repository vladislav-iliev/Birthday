package com.vladislaviliev.birthday.screens.kid

import androidx.compose.ui.graphics.ImageBitmap
import com.vladislaviliev.birthday.Theme

data class KidScreenState(
    val isActive: Boolean,
    val name: String,
    val ageMonths: Int,
    val theme: Theme,
    val avatar: ImageBitmap?
)