package com.vladislaviliev.birthday.networking

sealed class State {
    data class Connected(val received: Response? = null): State()
    data class Disconnected(val cause: Throwable? = null): State()
}