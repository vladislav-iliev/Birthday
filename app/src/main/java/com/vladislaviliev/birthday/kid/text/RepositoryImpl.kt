package com.vladislaviliev.birthday.kid.text

import com.vladislaviliev.birthday.networking.Api
import com.vladislaviliev.birthday.networking.getTextOrNull
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RepositoryImpl(scope: CoroutineScope, dispatcher: CoroutineDispatcher, api: Api) : Repository {

    override val text = api.networkState
        .map { it.getTextOrNull() }
        .flowOn(dispatcher)
        .stateIn(scope, SharingStarted.WhileSubscribed(5_000L), api.networkState.value.getTextOrNull())
}