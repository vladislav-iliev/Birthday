package com.vladislaviliev.birthday.screens.kid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.birthday.kid.StateTransformer
import com.vladislaviliev.birthday.networking.Api
import com.vladislaviliev.birthday.kid.avatar.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(api: Api, avatarRepository: Repository) : ViewModel() {

    private val transformer = StateTransformer()

    val state = combine(api.networkState, avatarRepository.bitmap, transformer::from)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            transformer.from(api.networkState.value, avatarRepository.bitmap.value)
        )
}