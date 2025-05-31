package com.vladislaviliev.birthday.networking

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
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
class ClientTest {

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
    fun `states progress up and down`() = runTest {
        val serverMessage = "{\"name\":\"Nanit\",\"dob\":1685826000000,\"theme\":\"pelican\"}"
        val serverMessageParsed = Json.Default.decodeFromString<MessageRaw>(serverMessage).beautify()

        val serverListener = object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                webSocket.send(serverMessage)
                webSocket.close(1000, "")
            }
        }
        mockWebServer.enqueue(MockResponse().setResponseCode(101).withWebSocketUpgrade(serverListener))
        val client = Client()

        val networkStates = mutableListOf<NetworkState>()

        backgroundScope.launch { client.networkState.toList(networkStates) }
        runCurrent()
        client.connect(mockWebServer.hostName, mockWebServer.port)

        advanceUntilIdle()
        Assert.assertEquals(NetworkState.Disconnected(), networkStates[0])
        Assert.assertEquals(NetworkState.Connecting, networkStates[1])
        Assert.assertEquals(NetworkState.Connected(), networkStates[2])
        Assert.assertEquals(NetworkState.Connected(serverMessageParsed), networkStates[3])
        Assert.assertTrue(networkStates[4] is NetworkState.Disconnected)
    }

    @Test
    fun testMultipleConnectsAreIdempotent() = runTest {

        val firstServerMessage = "{\"name\":\"Nanit\",\"dob\":1685826000000,\"theme\":\"pelican\"}"
        val firstParsed = Json.Default.decodeFromString<MessageRaw>(firstServerMessage).beautify()

        val serverListener = object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                webSocket.send(firstServerMessage)
                webSocket.close(1000, "")
            }
        }

        mockWebServer.enqueue(MockResponse().setResponseCode(101).withWebSocketUpgrade(serverListener))
        val client = Client()

        val networkStates = mutableListOf<NetworkState>()

        backgroundScope.launch { client.networkState.toList(networkStates) }
        val firstConnect = launch { client.connect(mockWebServer.hostName, mockWebServer.port) }
        val secondConnect = launch { client.connect(mockWebServer.hostName, mockWebServer.port) }
        runCurrent()
        firstConnect.join()
        secondConnect.join()

        Assert.assertEquals("Connected count", 1, networkStates.count(NetworkState.Connected()::equals))
        Assert.assertEquals("Parsed message", 1, networkStates.count(NetworkState.Connected(firstParsed)::equals))
    }
}