package com.vladislaviliev.birthday.screens.kid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.birthday.kid.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(repository: Repository) : ViewModel() {

    val state = repository.state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), repository.state.value)
}