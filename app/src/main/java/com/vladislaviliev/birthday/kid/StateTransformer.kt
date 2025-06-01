package com.vladislaviliev.birthday.kid

import androidx.compose.ui.graphics.ImageBitmap
import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.kid.text.Text
import com.vladislaviliev.birthday.networking.NetworkState
import com.vladislaviliev.birthday.networking.getTextOrNull

class StateTransformer {
    fun from(apiNetworkState: NetworkState, avatar: ImageBitmap?): State {
        val text = apiNetworkState.getTextOrNull()
        if (null == text) return State(false, Text("", Age(-1, false), Theme.PELICAN), null)
        return State(true, text, avatar)
    }
}