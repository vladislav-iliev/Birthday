package com.vladislaviliev.birthday

import com.vladislaviliev.birthday.networking.MSG_TO_SEND_ON_CONNECT
import com.vladislaviliev.birthday.networking.Client
import com.vladislaviliev.birthday.networking.State
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ConnectionTest {

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
        Assert.assertNull((Client("", 0).response.value as State.Disconnected).cause)
    }

    @Test
    fun testSuccessfulConnectionAndMessageExchange() = runTest {
        val serverListener = object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                if (text == MSG_TO_SEND_ON_CONNECT) webSocket.send("{\"name\":\"Nanit\",\"dob\":1685826000000,\"theme\":\"pelican\"}")
            }
        }
        mockWebServer.enqueue(MockResponse().setResponseCode(101).withWebSocketUpgrade(serverListener))

        val client = Client(mockWebServer.hostName, mockWebServer.port)
        backgroundScope.launch { client.connect() }

        val state = client.response.drop(2).first()
        Assert.assertTrue(state is State.Connected)
        Assert.assertNotNull((state as State.Connected).received)
    }

    @Test
    fun testServerDisconnectionHandling() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(101))
        val client = Client(mockWebServer.hostName, mockWebServer.port)
        backgroundScope.launch { client.connect() }
        mockWebServer.shutdown()

        val state = client.response.drop(1).first()
        Assert.assertTrue(state is State.Disconnected)
    }
}