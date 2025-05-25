package com.vladislaviliev.birthday.screens.connect

sealed class State {
    data class Message(val msg: String) : State()
    object Connecting : State()
}