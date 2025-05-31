package com.vladislaviliev.birthday.networking

sealed class NetworkState {
    data class Disconnected(val cause: Exception? = null) : NetworkState()
    data object Connecting : NetworkState()
    data class Connected(val message: Message? = null) : NetworkState()
}

fun NetworkState.getMessageOrNull() = (this as? NetworkState.Connected)?.message