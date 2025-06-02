package com.vladislaviliev.birthday.kid

import androidx.compose.ui.graphics.ImageBitmap
import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.kid.text.Text

class StateTransformer {
    fun from(text: Text?, avatar: ImageBitmap?) =
        if (null == text) State(false, Text("", Age(-1, false), Theme.PELICAN), null)
        else State(true, text, avatar)
}