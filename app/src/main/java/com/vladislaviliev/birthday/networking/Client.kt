package com.vladislaviliev.birthday.networking

import com.vladislaviliev.birthday.kid.KidApi
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.receiveDeserialized
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.websocket.Frame
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json

class Client : KidApi {

    private val client = HttpClient(CIO) {
        install(Logging)
        install(WebSockets) {
            contentConverter = KotlinxWebsocketSerializationConverter(Json)
        }
    }

    private val _Network_state = MutableStateFlow<NetworkState>(NetworkState.Disconnected())
    override val networkState = _Network_state.asStateFlow()

    private suspend fun loopReceiving(session: DefaultClientWebSocketSession) {
        while (true) {
            val response = session.receiveDeserialized<MessageRaw>().beautify()
            _Network_state.emitAndYield(NetworkState.Connected(response))
        }
    }

    private suspend fun onConnected(session: DefaultClientWebSocketSession) {
        _Network_state.emitAndYield(NetworkState.Connected())
        session.send(Frame.Text("HappyBirthday"))
        loopReceiving(session)
    }

    override suspend fun connect(ip: String, port: Int) {
        if (_Network_state.value is NetworkState.Connecting || _Network_state.value is NetworkState.Connected) return
        _Network_state.emitAndYield(NetworkState.Connecting)
        try {
            client.webSocket(host = ip, port = port, path = "/nanit", block = ::onConnected)
        } catch (e: Exception) {
            _Network_state.emitAndYield(NetworkState.Disconnected(e))
        }
    }
}