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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.serialization.json.Json
import java.util.concurrent.atomic.AtomicBoolean

class Client : Api {

    private val client = HttpClient(CIO) {
        install(Logging)
        install(WebSockets) {
            contentConverter = KotlinxWebsocketSerializationConverter(Json)
        }
    }

    private val isTransmitting = AtomicBoolean(false)

    private val _networkState = MutableSharedFlow<NetworkState>()
    override val networkState = _networkState.asSharedFlow()

    private suspend fun loopReceiving(session: DefaultClientWebSocketSession) {
        while (true) {
            val response = session.receiveDeserialized<TextRaw>().parse()
            _networkState.emit(NetworkState.Connected(response))
        }
    }

    private suspend fun onConnected(session: DefaultClientWebSocketSession) {
        _networkState.emit(NetworkState.Connected())
        session.send(Frame.Text("HappyBirthday"))
        loopReceiving(session)
    }

    override suspend fun connect(ip: String, port: Int) {
        if (!isTransmitting.compareAndSet(false, true)) return
        _networkState.emit(NetworkState.Connecting)
        try {
            client.webSocket(host = ip, port = port, path = "/nanit", block = ::onConnected)
        } catch (e: Exception) {
            _networkState.emit(NetworkState.Disconnected(e))
            isTransmitting.set(false)
        }
    }
}