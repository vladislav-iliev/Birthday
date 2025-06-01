package com.vladislaviliev.birthday.dependencies

import androidx.compose.ui.graphics.ImageBitmap
import com.vladislaviliev.birthday.kid.avatar.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import java.net.URI

class DummyAvatarRepository : Repository {

    override val fileUri: URI = URI.create("")

    override val bitmap = MutableStateFlow<ImageBitmap?>(null)

    override suspend fun onPhotoCopied() {
        TODO("Not yet implemented")
    }

    override suspend fun copyFromUri(uri: URI) {
        TODO("Not yet implemented")
    }
}