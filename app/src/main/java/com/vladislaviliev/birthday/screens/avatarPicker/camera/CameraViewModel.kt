package com.vladislaviliev.birthday.screens.avatarPicker.camera

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.birthday.kid.avatar.AvatarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(private val repository: AvatarRepository) : ViewModel() {

    fun copyFromUri(uri: Uri) {
        viewModelScope.launch { repository.copyFromUri(uri) }
    }
}