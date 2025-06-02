package com.vladislaviliev.birthday.kid.avatar

import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.coroutines.flow.StateFlow

interface Repository {
    val fileUri: Uri
    val bitmap: StateFlow<ImageBitmap?>
    suspend fun onPhotoCopied()
    suspend fun copyFromUri(uri: Uri)
}