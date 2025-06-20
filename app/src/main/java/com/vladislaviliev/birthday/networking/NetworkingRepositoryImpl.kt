package com.vladislaviliev.birthday.networking

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.receiveDeserialized
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.websocket.Frame
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class NetworkingRepositoryImpl(
    private val scope: CoroutineScope,
    private val dispatcher: CoroutineDispatcher,
) : NetworkingRepository {

    private val client = HttpClient(CIO) {
        install(Logging)
        install(WebSockets) {
            contentConverter = KotlinxWebsocketSerializationConverter(Json)
        }
    }

    private val _state = MutableStateFlow<NetworkState>(NetworkState.Disconnected())
    override val state = _state.asStateFlow()

    private suspend fun loopReceiving(session: DefaultClientWebSocketSession) {
        while (true) {
            val response = session.receiveDeserialized<TextRaw>().parse()
            _state.emit(NetworkState.Connected(response))
        }
    }

    private suspend fun onConnected(session: DefaultClientWebSocketSession) {
        _state.emit(NetworkState.Connected())
        session.send(Frame.Text("HappyBirthday"))
        loopReceiving(session)
    }

    private suspend fun connectWebSocket(ip: String, port: Int) = scope.launch(dispatcher) {
        _state.emit(NetworkState.Connecting)
        try {
            client.webSocket(host = ip, port = port, path = "/nanit", block = ::onConnected)
        } catch (e: Exception) {
            _state.emit(NetworkState.Disconnected(e))
        }
    }.join()

    override suspend fun connect(ip: String, port: Int) {
        if (_state.value !is NetworkState.Disconnected) return
        connectWebSocket(ip, port)
    }
}