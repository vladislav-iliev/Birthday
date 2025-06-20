package com.vladislaviliev.birthday.networking

import kotlinx.coroutines.flow.Flow

interface NetworkingRepository {
    val state: Flow<NetworkState>
    suspend fun connect(ip: String, port: Int)
}