package com.vladislaviliev.birthday.screens.kid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.birthday.kids.KidsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(kidsApi: KidsApi) : ViewModel() {

    val state = kidsApi.state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), kidsApi.state.value)

}