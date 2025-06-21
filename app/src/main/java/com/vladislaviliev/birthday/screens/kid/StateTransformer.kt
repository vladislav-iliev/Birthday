package com.vladislaviliev.birthday.screens.kid

import androidx.compose.ui.graphics.ImageBitmap
import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.kid.Age
import com.vladislaviliev.birthday.kid.State
import com.vladislaviliev.birthday.kid.text.Text
import com.vladislaviliev.birthday.networking.NetworkState

class StateTransformer {
    fun from(networkState: NetworkState, avatar: ImageBitmap?) = networkState.getTextOrNull()?.let {
        State(true, it, avatar)
    } ?: State(false, Text("", Age(-1, false), Theme.PELICAN), null)
}