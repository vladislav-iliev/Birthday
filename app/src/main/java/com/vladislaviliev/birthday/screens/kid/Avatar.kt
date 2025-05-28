package com.vladislaviliev.birthday.screens.kid

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vladislaviliev.birthday.R
import com.vladislaviliev.birthday.networking.Message

@Composable
fun Avatar(message: Message, modifier: Modifier = Modifier) {
    Image(
        painterResource(message.theme.avatarPlaceholderRes),
        stringResource(R.string.avatar_image_of_x, message.name),
        modifier
            .clip(CircleShape)
            .border(9.dp, colorResource(message.theme.avatarBorderColorRes), CircleShape)
    )
}