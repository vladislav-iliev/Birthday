package com.vladislaviliev.birthday.kid.avatar

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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val context: Context,
    private val scope: CoroutineScope,
    private val dispatcher: CoroutineDispatcher,
) : Repository {

    private val file = File(context.filesDir, "avatar")

    override val fileUri: Uri =
        FileProvider.getUriForFile(context, context.getString(R.string.file_provider_authority), file)

    private val _bitmap = MutableStateFlow<ImageBitmap?>(initialState())
    override val bitmap = _bitmap.asStateFlow()

    private fun initialState() = if (file.length() == 0L) null else convertFileToBitmap()

    private fun convertFileToBitmap() = BitmapFactory.decodeFile(file.absolutePath)?.rotate()?.asImageBitmap()

    private suspend fun emitFileAsBitmap() {
        _bitmap.emit(convertFileToBitmap())
    }

    private fun Bitmap.rotate(): Bitmap {
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

        return Bitmap.createBitmap(this, 0, 0, getWidth(), getHeight(), matrix, true)
    }

    override suspend fun onPhotoCopied() {
        emitFileAsBitmap()
    }

    override suspend fun copyFromUri(uri: Uri) = scope.launch(dispatcher) {
        context.contentResolver.openInputStream(uri)?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        emitFileAsBitmap()
    }.join()
}