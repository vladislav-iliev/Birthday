package com.vladislaviliev.birthday

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladislaviliev.birthday.avatarPicker.AvatarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(private val avatarRepository: AvatarRepository) : ViewModel() {

    val bitmap = avatarRepository.bitmap

    fun saveAvatarFromUri(uri: Uri) {
        viewModelScope.launch { avatarRepository.saveFromUri(uri) }
    }

    fun getUriOfFile(file: File) = avatarRepository.getShareableUri(file)

}