package com.vladislaviliev.birthday.networking

import kotlinx.coroutines.flow.Flow

interface Api {
    val networkState: Flow<NetworkState>
    suspend fun connect(ip: String, port: Int)
}