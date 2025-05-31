package com.vladislaviliev.birthday.screens.kid

import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
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
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Avatar(name: String, bitmap: ImageBitmap?, theme: Theme, onPickerClick: () -> Unit, modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier) { // seems like a reasonable choice instead of custom Layout
        val borderRadius = 9.dp
        Avatar(
            name,
            bitmap,
            theme,
            Modifier
                .aspectRatio(1f)
                .fillMaxSize()
                .align(Alignment.Center)
                .clip(CircleShape)
                .border(borderRadius, colorResource(theme.avatarBorderColorRes), CircleShape)
        )

        val size = borderRadius * 5
        val radiusPx = getRadiusPixels(borderRadius, LocalContext.current.resources.displayMetrics)
        val createOffset = { d: Density -> radiusToOffset(radiusPx) }
        Image(
            painterResource(theme.avatarPickerRes),
            stringResource(R.string.select_new_avatar),
            Modifier
                .align(Alignment.Center)
                .offset(createOffset)
                .size(size)
                .clip(CircleShape)
                .clickable(onClick = onPickerClick, onClickLabel = stringResource(R.string.select_new_avatar))
        )
    }
}

@Composable
private fun Avatar(name: String, bitmap: ImageBitmap?, theme: Theme, modifier: Modifier = Modifier) {

    val contentDescription = stringResource(R.string.avatar_image_of_x, name)
    val scale = ContentScale.Crop
    val alignment = Alignment.Center

    if (null == bitmap) {
        val painter = painterResource(theme.avatarPlaceholderRes)
        Image(painter, contentDescription, modifier, contentScale = scale, alignment = alignment)
        return
    }

    Image(bitmap, contentDescription, modifier, contentScale = scale, alignment = alignment)
}

fun BoxWithConstraintsScope.getRadiusPixels(borderRadius: Dp, displayMetrics: DisplayMetrics): Float {
    val borderRadiusPx =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, borderRadius.value.toFloat(), displayMetrics)
    val diameter = minOf(constraints.maxWidth, constraints.maxHeight) - borderRadiusPx
    return diameter / 2
}

private fun radiusToOffset(r: Float): IntOffset {
    val angleInRadians = Math.toRadians(45.0).toFloat()
    return IntOffset((r * cos(angleInRadians)).toInt(), -(r * sin(angleInRadians)).toInt())
}

@Preview
@Composable
private fun PreviewAvatar(modifier: Modifier = Modifier) {
    Avatar("Johny", null, Theme.ELEPHANT, {}, modifier.fillMaxSize())
}