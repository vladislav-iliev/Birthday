package com.vladislaviliev.birthday.networking

sealed class State {
    data class Disconnected(val cause: Exception? = null) : State()
    data object Connecting : State()
    data class Connected(val received: Response? = null) : State()
}