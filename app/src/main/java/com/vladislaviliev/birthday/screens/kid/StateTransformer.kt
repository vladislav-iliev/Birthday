package com.vladislaviliev.birthday.screens.kid

import androidx.compose.ui.graphics.ImageBitmap
import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.kid.Age
import com.vladislaviliev.birthday.kid.text.Text
import com.vladislaviliev.birthday.networking.NetworkState
import com.vladislaviliev.birthday.networking.getTextOrNull

class StateTransformer {
    fun from(apiNetworkState: NetworkState, avatar: ImageBitmap?): KidScreenState {
        val text = apiNetworkState.getTextOrNull()
        if (null == text) return KidScreenState(false, Text("", Age(-1, false), Theme.PELICAN), null)
        return KidScreenState(true, text, avatar)
    }
}