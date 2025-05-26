package com.vladislaviliev.birthday.screens.connect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.birthday.kids.KidsRepository
import com.vladislaviliev.birthday.networking.State
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ViewModel(private val repository: KidsRepository) : ViewModel() {

    val state = repository.state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), State.Disconnected())

    fun connect() {
        viewModelScope.launch { repository.connect() }
    }
}