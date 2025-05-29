package com.vladislaviliev.birthday.screens.kid

import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.vladislaviliev.birthday.R
import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.networking.Message
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Avatar(message: Message, onPickerClick: () -> Unit, modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier) { // seems like a reasonable choice instead of custom Layout

        val borderRadius = 9.dp
        val diameter = minOf(this.maxWidth, this.maxHeight) - borderRadius
        val pickerSize = borderRadius * 5

        Image(
            painterResource(message.theme.avatarPlaceholderRes),
            stringResource(R.string.avatar_image_of_x, message.name),
            Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .clip(CircleShape)
                .border(borderRadius, colorResource(message.theme.avatarBorderColorRes), CircleShape)
        )

        val displayMetrics = LocalContext.current.resources.displayMetrics
        val createOffset = { d: Density -> radiusToOffset(diameter / 2, displayMetrics) }

        Image(
            painterResource(message.theme.avatarPickerRes),
            stringResource(R.string.select_new_avatar),
            Modifier
                .align(Alignment.Center)
                .offset(createOffset)
                .size(pickerSize)
                .clip(CircleShape)
                .clickable(onClick = onPickerClick)
        )
    }
}

private fun radiusToOffset(r: Dp, displayMetrics: DisplayMetrics): IntOffset {
    val rPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, r.value, displayMetrics)
    val angleInRadians = Math.toRadians(45.0).toFloat()
    return IntOffset(
        x = (rPx * cos(angleInRadians)).toInt(),
        y = -(rPx * sin(angleInRadians)).toInt()
    )
}

@Preview
@Composable
private fun PreviewAvatar(modifier: Modifier = Modifier) {
    Avatar(Message("Johny", 1, Theme.ELEPHANT), {}, modifier.fillMaxSize())
}