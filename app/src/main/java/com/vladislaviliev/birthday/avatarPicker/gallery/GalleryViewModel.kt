package com.vladislaviliev.birthday.avatarPicker.gallery

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.birthday.avatarPicker.AvatarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(private val avatarRepository: AvatarRepository) : ViewModel() {

    val avatarUri = avatarRepository.fileUri

    fun copyAvatarFromUri(uri: Uri) {
        viewModelScope.launch { avatarRepository.copyFromUri(uri) }
    }
}