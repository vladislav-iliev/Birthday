package com.vladislaviliev.birthday.kids

import com.vladislaviliev.birthday.networking.Response
import com.vladislaviliev.birthday.networking.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class InMemoryKidsApi : KidsApi {

    private val _state = MutableStateFlow<State>(State.Disconnected())
    override val state = _state.asStateFlow()

    override suspend fun connect() {
        _state.emit(State.Connected())
    }

    suspend fun emitResponse(r: Response) {
        _state.emit(State.Connected(r))
    }
}