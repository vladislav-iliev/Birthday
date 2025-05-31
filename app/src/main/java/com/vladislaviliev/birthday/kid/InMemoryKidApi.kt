package com.vladislaviliev.birthday.kid

import com.vladislaviliev.birthday.networking.Message
import com.vladislaviliev.birthday.networking.State
import com.vladislaviliev.birthday.networking.emitAndYield
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class InMemoryKidApi : KidApi {

    private val _state = MutableStateFlow<State>(State.Disconnected())
    override val state = _state.asStateFlow()

    override suspend fun connect(ip: String, port: Int) {
        if (_state.value is State.Connecting || _state.value is State.Connected) return
        _state.emitAndYield(State.Connecting)
        _state.emitAndYield(State.Connected())
    }

    suspend fun emitMessage(r: Message) {
        _state.emitAndYield(State.Connected(r))
    }

    suspend fun disconnect() {
        _state.emitAndYield(State.Disconnected())
    }
}