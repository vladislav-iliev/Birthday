package com.vladislaviliev.birthday.kids

import com.vladislaviliev.birthday.networking.State
import kotlinx.coroutines.flow.StateFlow

const val MSG_TO_SEND_ON_CONNECT = "HappyBirthday"

interface KidsApi {
    val state: StateFlow<State>
    suspend fun connect()
}