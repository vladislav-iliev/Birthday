package com.vladislaviliev.birthday

import com.vladislaviliev.birthday.networking.Client
import com.vladislaviliev.birthday.networking.ResponseRaw
import com.vladislaviliev.birthday.networking.State
import com.vladislaviliev.birthday.networking.beautify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
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
    fun testInitialState() {
        Assert.assertEquals(State.Disconnected(), Client("", 0).state.value)
    }

    @Test
    fun testConnection() = runTest {
        val serverListener = object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                webSocket.close(1000, "")
            }
        }

        mockWebServer.enqueue(MockResponse().setResponseCode(101).withWebSocketUpgrade(serverListener))
        val client = Client(mockWebServer.hostName, mockWebServer.port)

        val states = mutableListOf<State>()

        backgroundScope.launch { client.state.collect { states.add(it) } }
        runCurrent()
        client.connect()
        runCurrent()
        Assert.assertEquals(State.Connecting, states[1])
        Assert.assertEquals(State.Connected(), states[2])
    }

    @Test
    fun testSuccessfulConnectionAndMessageExchange() = runTest {
        val serverListener = object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                webSocket.send("{\"name\":\"Nanit\",\"dob\":1685826000000,\"theme\":\"pelican\"}")
            }
        }
        mockWebServer.enqueue(MockResponse().setResponseCode(101).withWebSocketUpgrade(serverListener))

        val client = Client(mockWebServer.hostName, mockWebServer.port)
        backgroundScope.launch { client.connect() }

        client.state.takeWhile { (it as? State.Connected)?.received == null }
    }

    @Test
    fun testServerDisconnectionHandling() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(101))
        val client = Client(mockWebServer.hostName, mockWebServer.port)
        backgroundScope.launch { client.connect() }

        client.state.takeWhile { it !is State.Connected }.collect {}
        mockWebServer.shutdown()
        client.state.takeWhile { it !is State.Disconnected }.collect {}
    }

    @Test
    fun testMultipleConnectsAreIdempotent() = runTest {

        val firstServerMessage = "{\"name\":\"Nanit\",\"dob\":1685826000000,\"theme\":\"pelican\"}"
        val firstParsed = Json.decodeFromString<ResponseRaw>(firstServerMessage).beautify()

        val secondServerMessage = "{\"name\":\"Nanit2\",\"dob\":1685826000000,\"theme\":\"pelican\"}"

        val serverListener = object : WebSocketListener() {
            var counter = 0
            override fun onMessage(webSocket: WebSocket, text: String) {
                if (counter++ == 0) webSocket.send(firstServerMessage)
                else webSocket.send(secondServerMessage)
            }
        }

        mockWebServer.enqueue(MockResponse().setResponseCode(101).withWebSocketUpgrade(serverListener))
        val client = Client(mockWebServer.hostName, mockWebServer.port)

        backgroundScope.launch { client.connect() }
        client.state.takeWhile { (it as? State.Connected)?.received == null }.collect {}
        Assert.assertEquals(firstParsed, (client.state.value as State.Connected).received)

        backgroundScope.launch { client.connect() }
        Assert.assertEquals(firstParsed, (client.state.value as State.Connected).received)
    }
}