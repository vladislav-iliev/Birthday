package com.vladislaviliev.birthday.screens.kid

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vladislaviliev.birthday.R

@Composable
fun Avatar(modifier: Modifier = Modifier) {
    Image(
        painterResource(R.drawable.avatar_placeholder_blue),
        null,
        modifier
            .clip(CircleShape)
            .border(9.dp, Color.Blue, CircleShape)
    )
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_5")
@Composable
fun AvatarPreview() {
    Avatar(Modifier.fillMaxSize())
}