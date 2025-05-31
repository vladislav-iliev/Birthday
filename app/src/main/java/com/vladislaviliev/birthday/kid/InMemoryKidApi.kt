package com.vladislaviliev.birthday.kid

import com.vladislaviliev.birthday.networking.NetworkMessage
import com.vladislaviliev.birthday.networking.NetworkState
import com.vladislaviliev.birthday.networking.emitAndYield
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class InMemoryKidApi : KidApi {

    private val _Network_state = MutableStateFlow<NetworkState>(NetworkState.Disconnected())
    override val networkState = _Network_state.asStateFlow()

    override suspend fun connect(ip: String, port: Int) {
        if (_Network_state.value is NetworkState.Connecting || _Network_state.value is NetworkState.Connected) return
        _Network_state.emitAndYield(NetworkState.Connecting)
        _Network_state.emitAndYield(NetworkState.Connected())
    }

    suspend fun emitMessage(r: NetworkMessage) {
        _Network_state.emitAndYield(NetworkState.Connected(r))
    }

    suspend fun disconnect() {
        _Network_state.emitAndYield(NetworkState.Disconnected())
    }
}