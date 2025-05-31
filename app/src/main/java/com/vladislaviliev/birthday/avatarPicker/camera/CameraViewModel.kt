package com.vladislaviliev.birthday.avatarPicker.camera

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.birthday.avatarPicker.AvatarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(private val avatarRepository: AvatarRepository) : ViewModel() {

    val avatarUri = avatarRepository.fileUri

    fun onPhotoCopied() {
        viewModelScope.launch { avatarRepository.onPhotoCopied() }
    }
}