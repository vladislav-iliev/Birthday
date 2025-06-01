package com.vladislaviliev.birthday.kid.avatar

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.coroutines.flow.StateFlow
import java.net.URI

interface Repository {
    val fileUri: URI
    val bitmap: StateFlow<ImageBitmap?>
    suspend fun onPhotoCopied()
    suspend fun copyFromUri(uri: URI)
}