package com.vladislaviliev.birthday.screens.kid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.birthday.kid.avatar.AvatarRepository
import com.vladislaviliev.birthday.networking.NetworkingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    networkingRepository: NetworkingRepository, avatarRepository: AvatarRepository
) : ViewModel() {

    val state = combine(networkingRepository.state, avatarRepository.bitmap, StateTransformer()::from)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            StateTransformer().from(networkingRepository.state.value, avatarRepository.bitmap.value)
        )
}