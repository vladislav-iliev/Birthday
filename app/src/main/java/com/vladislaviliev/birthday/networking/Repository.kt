package com.vladislaviliev.birthday.networking

import kotlinx.coroutines.flow.Flow

interface Repository {
    val state: Flow<NetworkState>
    suspend fun connect(ip: String, port: Int)
}