package com.vladislaviliev.birthday.dependencies

import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import com.vladislaviliev.birthday.kid.avatar.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DummyAvatarRepository : Repository {

    override val fileUri = Uri.EMPTY

    override val bitmap = MutableStateFlow<ImageBitmap?>(null)

    override suspend fun onPhotoCopied() {
        TODO("Not yet implemented")
    }

    override suspend fun copyFromUri(uri: Uri) {
        TODO("Not yet implemented")
    }
}