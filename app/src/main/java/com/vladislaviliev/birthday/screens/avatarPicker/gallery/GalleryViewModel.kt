package com.vladislaviliev.birthday.screens.avatarPicker.gallery

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.birthday.kid.avatar.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.URI
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun copyAvatarFromUri(uri: Uri) {
        viewModelScope.launch { repository.copyFromUri(URI.create(uri.toString())) }
    }
}