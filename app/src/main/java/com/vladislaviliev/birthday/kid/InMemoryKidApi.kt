package com.vladislaviliev.birthday.kid

import com.vladislaviliev.birthday.kid.text.Text
import com.vladislaviliev.birthday.networking.NetworkState
import com.vladislaviliev.birthday.networking.emitAndYield
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class InMemoryKidApi : KidApi {

    private val _networkState = MutableStateFlow<NetworkState>(NetworkState.Disconnected())
    override val networkState = _networkState.asStateFlow()

    override suspend fun connect(ip: String, port: Int) {
        if (_networkState.value is NetworkState.Connecting || _networkState.value is NetworkState.Connected) return
        _networkState.emitAndYield(NetworkState.Connecting)
        _networkState.emitAndYield(NetworkState.Connected())
    }

    suspend fun emit(t: Text) {
        _networkState.emitAndYield(NetworkState.Connected(t))
    }

    suspend fun disconnect() {
        _networkState.emitAndYield(NetworkState.Disconnected())
    }
}