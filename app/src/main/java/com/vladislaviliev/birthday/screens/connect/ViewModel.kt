package com.vladislaviliev.birthday.screens.connect

import androidx.lifecycle.ViewModel
import com.vladislaviliev.birthday.kids.KidsRepository
import com.vladislaviliev.birthday.networking.State
import kotlinx.coroutines.flow.map

class ViewModel(private val repository: KidsRepository) : ViewModel() {

    private val _connected = repository.state.map {
        if (it is State.Disconnected) false
    }
}