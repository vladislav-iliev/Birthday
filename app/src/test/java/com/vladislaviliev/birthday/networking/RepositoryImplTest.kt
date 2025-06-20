package com.vladislaviliev.birthday.networking

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryImplTest {

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `states progress up and down`() = runTest(UnconfinedTestDispatcher()) {
        val serverMessage = "{\"name\":\"Nanit\",\"dob\":1685826000000,\"theme\":\"pelican\"}"
        val serverMessageParsed = Json.Default.decodeFromString<TextRaw>(serverMessage).parse()

        val serverListener = object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                webSocket.send(serverMessage)
                webSocket.close(1000, "")
            }
        }
        mockWebServer.enqueue(MockResponse().setResponseCode(101).withWebSocketUpgrade(serverListener))
        val repo = NetworkingRepositoryImpl(this, coroutineContext[CoroutineDispatcher]!!)

        val networkStates = mutableListOf<NetworkState>()

        backgroundScope.launch { repo.state.toList(networkStates) }
        repo.connect(mockWebServer.hostName, mockWebServer.port)

        Assert.assertEquals(
            listOf(
                NetworkState.Disconnected(),
                NetworkState.Connecting,
                NetworkState.Connected(),
                NetworkState.Connected(serverMessageParsed)
            ), networkStates.take(4)
        )
        Assert.assertTrue(networkStates.last() is NetworkState.Disconnected)
    }

    @Test
    fun testMultipleConnectsAreIdempotent() = runTest(UnconfinedTestDispatcher()) {

        val firstServerMessage = "{\"name\":\"Nanit\",\"dob\":1685826000000,\"theme\":\"pelican\"}"
        val firstParsed = Json.Default.decodeFromString<TextRaw>(firstServerMessage).parse()

        val serverListener = object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                webSocket.send(firstServerMessage)
                webSocket.close(1000, "")
            }
        }

        mockWebServer.enqueue(MockResponse().setResponseCode(101).withWebSocketUpgrade(serverListener))
        val client = NetworkingRepositoryImpl(this, coroutineContext[CoroutineDispatcher]!!)

        val networkStates = mutableListOf<NetworkState>()

        backgroundScope.launch { client.state.toList(networkStates) }
        val firstConnect = launch { client.connect(mockWebServer.hostName, mockWebServer.port) }
        val secondConnect = launch { client.connect(mockWebServer.hostName, mockWebServer.port) }
        firstConnect.join()
        firstConnect.join()
        secondConnect.join()

        Assert.assertEquals(1, networkStates.count(NetworkState.Connected()::equals))
        Assert.assertEquals(1, networkStates.count(NetworkState.Connected(firstParsed)::equals))
    }
}