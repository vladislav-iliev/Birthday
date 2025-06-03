package com.vladislaviliev.birthday.screens.connect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.birthday.networking.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.vladislaviliev.birthday.kid.avatar.Repository as AvatarRepository
import com.vladislaviliev.birthday.kid.text.Repository as TextRepository
import com.vladislaviliev.birthday.networking.Repository as NetworkingRepository

@HiltViewModel
class ViewModel @Inject constructor(
    private val networkingRepository: NetworkingRepository,
    textRepository: TextRepository, // To trigger early instantiation for App Dependency Container. Don't remove!
    avatarRepository: AvatarRepository // To trigger early instantiation for App Dependency Container. Don't remove!
) : ViewModel() {

    val networkState = networkingRepository.state
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NetworkState.Disconnected())

    fun connect(ip: String, port: Int) {
        viewModelScope.launch { networkingRepository.connect(ip, port) }
    }
}