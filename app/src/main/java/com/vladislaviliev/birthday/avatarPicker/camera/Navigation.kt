package com.vladislaviliev.birthday.avatarPicker.camera

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import kotlinx.serialization.Serializable

@Serializable
object CameraPermissionRoute

fun NavGraphBuilder.addCameraPermissionRequestDestination(
    onDismissRequest: () -> Unit,
    onPermissionGranted: () -> Unit,
) {
    dialog<CameraPermissionRoute> { Content(onDismissRequest, onPermissionGranted) }
}

@Composable
private fun Content(onDismissRequest: () -> Unit, onPermissionGranted: () -> Unit) {
    CameraPermissionDialog(onDismissRequest, onPermissionGranted)
}

fun NavController.navigateToCameraPermission() {
    popBackStack()
    navigate(CameraPermissionRoute)
}

fun NavController.onPermissionGranted() {
    popBackStack()
}