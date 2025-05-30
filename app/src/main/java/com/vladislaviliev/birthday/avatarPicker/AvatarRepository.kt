package com.vladislaviliev.birthday.avatarPicker

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import com.vladislaviliev.birthday.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class AvatarRepository @Inject constructor(
    private val context: Context,
    private val dispatcher: CoroutineDispatcher,
) {
    private val file = File(context.filesDir, "avatar")
    val fileUri: Uri = FileProvider.getUriForFile(context, context.getString(R.string.file_provider_authority), file)

    private val _state = MutableStateFlow<ImageBitmap?>(initialState())
    val state = _state.asStateFlow()

    private fun initialState() = if (file.length() == 0L) null else convertFileToBitmap()

    private fun convertFileToBitmap() =
        BitmapFactory.decodeFile(file.absolutePath)?.run { rotateBitmap(this).asImageBitmap() }

    private suspend fun emitFileAsBitmap() {
        _state.emit(convertFileToBitmap())
    }

    private fun rotateBitmap(bitmap: Bitmap): Bitmap {
        val exif = ExifInterface(file.absolutePath)
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

        val rotate = when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_270 -> 270
            ExifInterface.ORIENTATION_ROTATE_180 -> 180
            ExifInterface.ORIENTATION_ROTATE_90 -> 90
            else -> 0
        }
        val matrix = Matrix()
        matrix.postRotate(rotate.toFloat())

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true)
    }

    suspend fun onPhotoCopied() {
        emitFileAsBitmap()
    }

    suspend fun copyFromUri(uri: Uri) = withContext(dispatcher) {
        context.contentResolver.openInputStream(uri)?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        emitFileAsBitmap()
    }
}