package com.vladislaviliev.birthday.networking

import kotlinx.coroutines.flow.StateFlow

interface Api {
    val networkState: StateFlow<NetworkState>
    suspend fun connect(ip: String, port: Int)
}