package com.vladislaviliev.birthday.avatarPicker.chooseSource

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import kotlinx.serialization.Serializable

@Serializable
object ChooseSourceRoute

fun NavGraphBuilder.addChooseSourceDestination(
    onDismissRequest: () -> Unit,
    onGalleryPick: () -> Unit,
    onCameraPick: () -> Unit,
) {
    dialog<ChooseSourceRoute> { Content(onDismissRequest, onGalleryPick, onCameraPick) }
}

@Composable
private fun Content(onDismissRequest: () -> Unit, onGalleryPick: () -> Unit, onCameraPick: () -> Unit) {
    ChooseSourceDialog(onDismissRequest, onGalleryPick, onCameraPick)
}