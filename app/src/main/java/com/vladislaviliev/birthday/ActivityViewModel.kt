package com.vladislaviliev.birthday

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.birthday.avatarPicker.AvatarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(private val avatarRepository: AvatarRepository) : ViewModel() {

    val state = avatarRepository.state.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val fileUri = avatarRepository.fileUri

    fun onPhotoCopied() {
        viewModelScope.launch { avatarRepository.onPhotoCopied() }
    }

    fun copyFromUri(uri: Uri) {
        viewModelScope.launch { avatarRepository.copyFromUri(uri) }
    }
}