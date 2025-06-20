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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.util.concurrent.atomic.AtomicBoolean

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

    private val isTransmitting = AtomicBoolean(false)

    private val _state = MutableSharedFlow<NetworkState>()
    override val state = _state.asSharedFlow()

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
            isTransmitting.set(false)
        }
    }.join()

    override suspend fun connect(ip: String, port: Int) {
        if (!isTransmitting.compareAndSet(false, true)) return
        connectWebSocket(ip, port)
    }
}