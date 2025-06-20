package com.vladislaviliev.birthday.test

import com.vladislaviliev.birthday.kid.text.Text
import com.vladislaviliev.birthday.networking.NetworkState
import com.vladislaviliev.birthday.networking.NetworkingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DummyNetworkingRepository : NetworkingRepository {

    private val _state = MutableStateFlow<NetworkState>(NetworkState.Disconnected())
    override val state = _state.asStateFlow()

    override suspend fun connect(ip: String, port: Int) {
        if (_state.value !is NetworkState.Disconnected) return
        _state.emit(NetworkState.Connecting)
        _state.emit(NetworkState.Connected())
    }

    suspend fun emit(t: Text) {
        _state.emit(NetworkState.Connected(t))
    }

    suspend fun disconnect() {
        _state.emit(NetworkState.Disconnected())
    }
}