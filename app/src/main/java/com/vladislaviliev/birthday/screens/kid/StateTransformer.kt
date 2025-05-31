package com.vladislaviliev.birthday.screens.kid

import androidx.compose.ui.graphics.ImageBitmap
import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.networking.NetworkState
import com.vladislaviliev.birthday.networking.getMessageOrNull

class StateTransformer {
    fun from(apiNetworkState: NetworkState, avatar: ImageBitmap?): KidScreenState {
        val message = apiNetworkState.getMessageOrNull()
        if (null == message) return KidScreenState(false, "", -1, Theme.PELICAN, null)
        return KidScreenState(true, message.name, message.ageMonths, message.theme, avatar)
    }
}