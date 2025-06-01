package com.vladislaviliev.birthday.screens.avatarPicker.camera

import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.birthday.kid.avatar.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val avatarUri = repository.fileUri.toString().toUri()

    fun onPhotoCopied() {
        viewModelScope.launch { repository.onPhotoCopied() }
    }
}