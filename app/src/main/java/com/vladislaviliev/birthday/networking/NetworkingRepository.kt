package com.vladislaviliev.birthday.networking

import kotlinx.coroutines.flow.StateFlow

interface NetworkingRepository {
    val state: StateFlow<NetworkState>
    suspend fun connect(ip: String, port: Int)
}