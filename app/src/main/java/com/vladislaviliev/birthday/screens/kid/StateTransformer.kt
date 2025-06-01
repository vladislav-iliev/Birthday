package com.vladislaviliev.birthday.screens.kid

import androidx.compose.ui.graphics.ImageBitmap
import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.kid.Age
import com.vladislaviliev.birthday.networking.NetworkState
import com.vladislaviliev.birthday.networking.getMessageOrNull

class StateTransformer {
    val defaultState = KidScreenState(false, "", Age(-1, false), Theme.PELICAN, null)

    fun from(apiNetworkState: NetworkState, avatar: ImageBitmap?): KidScreenState {
        val message = apiNetworkState.getMessageOrNull()
        if (null == message) return defaultState
        return KidScreenState(true, message.name, message.age, message.theme, avatar)
    }
}