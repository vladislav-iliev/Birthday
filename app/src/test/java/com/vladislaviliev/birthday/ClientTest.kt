package com.vladislaviliev.birthday

import com.vladislaviliev.birthday.networking.Client
import com.vladislaviliev.birthday.networking.ResponseRaw
import com.vladislaviliev.birthday.networking.State
import com.vladislaviliev.birthday.networking.beautify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.takeWhile
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
        val serverMessageParsed = Json.decodeFromString<ResponseRaw>(serverMessage).beautify()

        val serverListener = object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                webSocket.send(serverMessage)
                webSocket.close(1000, "")
            }
        }
        mockWebServer.enqueue(MockResponse().setResponseCode(101).withWebSocketUpgrade(serverListener))
        val client = Client(mockWebServer.hostName, mockWebServer.port)

        val states = mutableListOf<State>()

        backgroundScope.launch { client.state.toList(states) }
        runCurrent()
        client.connect()

        advanceUntilIdle()
        Assert.assertEquals(State.Disconnected(), states[0])
        Assert.assertEquals(State.Connecting, states[1])
        Assert.assertEquals(State.Connected(), states[2])
        Assert.assertEquals(State.Connected(serverMessageParsed), states[3])
        Assert.assertTrue(states[4] is State.Disconnected)
    }

    @Test
    fun testMultipleConnectsAreIdempotent() = runTest {

        val firstServerMessage = "{\"name\":\"Nanit\",\"dob\":1685826000000,\"theme\":\"pelican\"}"
        val firstParsed = Json.decodeFromString<ResponseRaw>(firstServerMessage).beautify()

        val serverListener = object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                webSocket.send(firstServerMessage)
                webSocket.close(1000, "")
            }
        }

        mockWebServer.enqueue(MockResponse().setResponseCode(101).withWebSocketUpgrade(serverListener))
        val client = Client(mockWebServer.hostName, mockWebServer.port)

        val states = mutableListOf<State>()

        backgroundScope.launch { client.state.toList(states) }
        val firstConnect = launch { client.connect() }
        val secondConnect = launch { client.connect() }
        runCurrent()
        firstConnect.join()
        secondConnect.join()

        Assert.assertEquals("Connected count", 1, states.count(State.Connected()::equals))
        Assert.assertEquals("Parsed message", 1, states.count(State.Connected(firstParsed)::equals))
    }
}