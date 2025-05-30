package com.vladislaviliev.birthday.avatarPicker

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.vladislaviliev.birthday.R
import java.io.File

class AvatarSaver(private val context: Context) {
    val file = File(context.filesDir, "avatar")
    val fileUri: Uri = FileProvider.getUriForFile(context, context.getString(R.string.file_provider_authority), file)

    fun saveUriToFile(uri: Uri): Uri {
        context.contentResolver.openInputStream(uri)?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return fileUri
    }
}