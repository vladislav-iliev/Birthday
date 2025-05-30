package com.vladislaviliev.birthday.avatarPicker.camera.permission

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import com.vladislaviliev.birthday.avatarPicker.camera.navigateToCamera
import kotlinx.serialization.Serializable

@Serializable
object CameraPermissionRoute

fun NavGraphBuilder.addCameraPermissionDestination(
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
    navigateToCamera()
}