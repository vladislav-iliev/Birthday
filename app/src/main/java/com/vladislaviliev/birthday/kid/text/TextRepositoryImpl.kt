package com.vladislaviliev.birthday.kid.text

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import com.vladislaviliev.birthday.networking.Repository as NetworkingRepository

class TextRepositoryImpl(
    scope: CoroutineScope,
    dispatcher: CoroutineDispatcher,
    networkingRepository: NetworkingRepository
) : TextRepository {

    override val text = networkingRepository.state
        .map { it.getTextOrNull() }.flowOn(dispatcher).stateIn(scope, SharingStarted.Eagerly, null)
}