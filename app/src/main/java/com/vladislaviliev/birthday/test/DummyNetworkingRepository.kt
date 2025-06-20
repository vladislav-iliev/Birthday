package com.vladislaviliev.birthday.test

import com.vladislaviliev.birthday.kid.text.Text
import com.vladislaviliev.birthday.networking.NetworkState
import com.vladislaviliev.birthday.networking.NetworkingRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.util.concurrent.atomic.AtomicBoolean

class DummyNetworkingRepository : NetworkingRepository {

    private val isTransmitting = AtomicBoolean(false)

    private val _state = MutableSharedFlow<NetworkState>()
    override val state = _state.asSharedFlow()

    override suspend fun connect(ip: String, port: Int) {
        if (!isTransmitting.compareAndSet(false, true)) return
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