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

class Client : KidsApi {

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
            _state.emitAndYield(State.Connected(response))
        }
    }

    private suspend fun onConnected(session: DefaultClientWebSocketSession) {
        _state.emitAndYield(State.Connected())
        session.send(Frame.Text("HappyBirthday"))
        loopReceiving(session)
    }

    override suspend fun connect(ip: String, port: Int) {
        if (_state.value is State.Connecting || _state.value is State.Connected) return
        _state.emitAndYield(State.Connecting)
        try {
            client.webSocket(host = ip, port = port, path = "/nanit", block = ::onConnected)
        } catch (e: Exception) {
            _state.emitAndYield(State.Disconnected(e))
        }
    }
}