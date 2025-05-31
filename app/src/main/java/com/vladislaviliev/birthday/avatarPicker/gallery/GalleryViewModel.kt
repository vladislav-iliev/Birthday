package com.vladislaviliev.birthday.avatarPicker.gallery

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.birthday.kid.avatar.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val avatarUri = repository.fileUri

    fun copyAvatarFromUri(uri: Uri) {
        viewModelScope.launch { repository.copyFromUri(uri) }
    }
}