package com.vladislaviliev.birthday.kid

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import com.vladislaviliev.birthday.kid.avatar.AvatarRepository
import com.vladislaviliev.birthday.kid.text.TextRepository

class Repository(
    scope: CoroutineScope,
    dispatcher: CoroutineDispatcher,
    textRepository: TextRepository,
    avatarRepository: AvatarRepository
) {

    private val transformer = StateTransformer()

    val state = combine(textRepository.text, avatarRepository.bitmap, transformer::from)
        .flowOn(dispatcher)
        .stateIn(
            scope,
            SharingStarted.WhileSubscribed(5_000L),
            transformer.from(textRepository.text.value, avatarRepository.bitmap.value)
        )
}