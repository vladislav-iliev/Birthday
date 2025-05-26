package com.vladislaviliev.birthday.networking

import com.vladislaviliev.birthday.kids.KidsApi
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

class Client(private val serverIp: String, private val port: Int) : KidsApi {

    private val client = HttpClient(CIO) {
        install(Logging)
        install(WebSockets) {
            contentConverter = KotlinxWebsocketSerializationConverter(Json)
        }
    }

    private val _state = MutableStateFlow<State>(State.Disconnected())
    override val state = _state.asStateFlow()

    private suspend fun loopReceiving(session: DefaultClientWebSocketSession) {
        while (true) {
            val response = session.receiveDeserialized<ResponseRaw>().beautify()
            _state.emit(State.Connected(response))
        }
    }

    private suspend fun onConnected(session: DefaultClientWebSocketSession) {
        _state.emit(State.Connected())
        session.send(Frame.Text("HappyBirthday"))
        loopReceiving(session)
    }

    override suspend fun connect() {
        if (state.value is State.Connecting || state.value is State.Connected) return
        _state.emit(State.Connecting)
        try {
            client.webSocket(host = serverIp, port = port, path = "/nanit", block = ::onConnected)
        } catch (e: Exception) {
            _state.emit(State.Disconnected(e))
        }
    }
}