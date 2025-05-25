package com.vladislaviliev.birthday.kids

import com.vladislaviliev.birthday.networking.State
import kotlinx.coroutines.flow.StateFlow

class KidsRepository(private val api: KidsApi) {

    val state: StateFlow<State> = api.state

    suspend fun connect() {
        api.connect()
    }
}