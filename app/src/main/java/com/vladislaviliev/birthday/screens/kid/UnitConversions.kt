package com.vladislaviliev.birthday.screens.kid

import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

//
//
//
//  UNUSED
//
//
fun Int.ptToDp(displayMetrics: DisplayMetrics): Dp {
    val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, toFloat(), displayMetrics)
    return (px / displayMetrics.density).dp
}

val Int.ptToDp @Composable get() = ptToDp(LocalContext.current.resources.displayMetrics)