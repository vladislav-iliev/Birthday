package com.vladislaviliev.birthday.kids

import com.vladislaviliev.birthday.networking.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class KidsRepository(
    private val scope: CoroutineScope,
    private val dispatcher: CoroutineDispatcher,
    private val api: KidsApi,
) {
    val state: Flow<State> = api.state

    suspend fun connect(ip: String, port: Int) = scope.launch(dispatcher) {
        api.connect(ip, port)
    }.join()
}