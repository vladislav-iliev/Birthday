package com.vladislaviliev.birthday.networking

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class RepositoryImpl(private val scope: CoroutineScope, private val dispatcher: CoroutineDispatcher, private val api: Api): Repository {

    override val networkState = api.networkState

    override suspend fun connect(ip: String, port: Int) = scope.launch(dispatcher) {
        api.connect(ip, port)
    }.join()
}