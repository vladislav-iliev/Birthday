package com.vladislaviliev.birthday.kids

import com.vladislaviliev.birthday.networking.Response
import com.vladislaviliev.birthday.networking.State
import com.vladislaviliev.birthday.networking.emitAndYield
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class InMemoryKidsApi : KidsApi {

    private val _state = MutableStateFlow<State>(State.Disconnected())
    override val state = _state.asStateFlow()

    override suspend fun connect(ip: String, port: Int) {
        if (_state.value is State.Connecting || _state.value is State.Connected) return
        _state.emitAndYield(State.Connecting)
        _state.emitAndYield(State.Connected())
    }

    suspend fun emitResponse(r: Response) {
        _state.emitAndYield(State.Connected(r))
    }

    suspend fun disconnect() {
        _state.emitAndYield(State.Disconnected())
    }
}