package com.vladislaviliev.birthday.kids

import com.vladislaviliev.birthday.networking.State
import kotlinx.coroutines.flow.Flow

class KidsRepository(private val api: KidsApi) {

    val state: Flow<State> = api.state

    suspend fun connect() {
        api.connect()
    }
}