package com.vladislaviliev.birthday.kid

import com.vladislaviliev.birthday.networking.NetworkState
import kotlinx.coroutines.flow.StateFlow

interface KidApi {
    val networkState: StateFlow<NetworkState>
    suspend fun connect(ip: String, port: Int)
}