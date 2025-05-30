package com.vladislaviliev.birthday.avatarPicker

import android.content.Context
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.core.content.FileProvider
import com.vladislaviliev.birthday.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

class AvatarRepository(
    private val context: Context,
    private val scope: CoroutineScope,
    private val dispatcher: CoroutineDispatcher,
) {
    private val fileProviderAuthority = context.getString(R.string.file_provider_authority)
    private val avatarFile = File(context.filesDir, "avatar").apply { createNewFile() }

    private val _bitmap = MutableStateFlow<ImageBitmap?>(null)
    val bitmap = _bitmap.asStateFlow()

    suspend fun saveFromUri(uri: Uri) = scope.launch(dispatcher) {
        context.contentResolver.openInputStream(uri)?.use { input ->
            avatarFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
    }.join()

    fun getShareableUri(file: File): Uri = FileProvider.getUriForFile(context, fileProviderAuthority, file)
}