package com.vladislaviliev.birthday.kids

import com.vladislaviliev.birthday.networking.State
import kotlinx.coroutines.flow.Flow

interface KidsApi {
    val state: Flow<State>
    suspend fun connect(ip: String, port: Int)
}