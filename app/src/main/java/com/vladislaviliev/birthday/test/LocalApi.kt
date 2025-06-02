package com.vladislaviliev.birthday.test

import com.vladislaviliev.birthday.kid.text.Text
import com.vladislaviliev.birthday.networking.Api
import com.vladislaviliev.birthday.networking.NetworkState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.util.concurrent.atomic.AtomicBoolean

class LocalApi : Api {

    private val isTransmitting = AtomicBoolean(false)

    private val _networkState = MutableSharedFlow<NetworkState>()
    override val networkState = _networkState.asSharedFlow()

    override suspend fun connect(ip: String, port: Int) {
        if (!isTransmitting.compareAndSet(false, true)) return
        _networkState.emit(NetworkState.Connecting)
        _networkState.emit(NetworkState.Connected())
    }

    suspend fun emit(t: Text) {
        _networkState.emit(NetworkState.Connected(t))
    }

    suspend fun disconnect() {
        _networkState.emit(NetworkState.Disconnected())
    }
}