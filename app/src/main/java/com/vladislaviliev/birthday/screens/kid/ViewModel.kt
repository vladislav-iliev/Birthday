package com.vladislaviliev.birthday.screens.kid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.birthday.kid.avatar.Repository
import com.vladislaviliev.birthday.kid.KidApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(kidApi: KidApi, avatarRepository: Repository) : ViewModel() {

    private val transformer = StateTransformer()

    val state = combine(kidApi.networkState, avatarRepository.state, transformer::from)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            transformer.from(kidApi.networkState.value, avatarRepository.state.value)
        )
}