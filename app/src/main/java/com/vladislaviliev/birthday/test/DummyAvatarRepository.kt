package com.vladislaviliev.birthday.test

import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.createBitmap
import com.vladislaviliev.birthday.kid.avatar.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DummyAvatarRepository : Repository {

    private val _bitmap = MutableStateFlow<ImageBitmap?>(null)
    override val bitmap = _bitmap.asStateFlow()

    private fun createDummyBitmap() = createBitmap(1, 1).asImageBitmap()

    var lastCopiedUri: Uri? = null
        private set

    override suspend fun copyFromUri(uri: Uri) {
        lastCopiedUri = uri
        _bitmap.emit(createDummyBitmap())
    }

    suspend fun clearState() {
        _bitmap.emit(null)
        lastCopiedUri = null
    }
}