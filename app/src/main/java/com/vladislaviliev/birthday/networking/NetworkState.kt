package com.vladislaviliev.birthday.networking

import com.vladislaviliev.birthday.kid.text.Text

sealed class NetworkState {
    data class Disconnected(val cause: Exception? = null) : NetworkState()
    data object Connecting : NetworkState()
    data class Connected(val text: Text? = null) : NetworkState()

    fun getTextOrNull() = (this as? Connected)?.text
}