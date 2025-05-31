package com.vladislaviliev.birthday.screens.kid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.birthday.avatarPicker.AvatarRepository
import com.vladislaviliev.birthday.kids.KidsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(kidsApi: KidsApi, avatarRepository: AvatarRepository) : ViewModel() {

    private val transformer = StateTransformer()

    val state = combine(kidsApi.state, avatarRepository.state, transformer::from)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            transformer.from(kidsApi.state.value, avatarRepository.state.value)
        )
}