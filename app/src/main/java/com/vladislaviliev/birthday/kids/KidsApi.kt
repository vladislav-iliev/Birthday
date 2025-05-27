package com.vladislaviliev.birthday.kids

import com.vladislaviliev.birthday.networking.State
import kotlinx.coroutines.flow.StateFlow

interface KidsApi {
    val state: StateFlow<State>
    suspend fun connect(ip: String, port: Int)
}