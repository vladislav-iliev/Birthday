package com.vladislaviliev.birthday.kid

import com.vladislaviliev.birthday.networking.State
import kotlinx.coroutines.flow.StateFlow

interface KidApi {
    val state: StateFlow<State>
    suspend fun connect(ip: String, port: Int)
}