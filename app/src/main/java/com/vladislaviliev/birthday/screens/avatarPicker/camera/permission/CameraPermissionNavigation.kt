package com.vladislaviliev.birthday.screens.avatarPicker.camera.permission

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.vladislaviliev.birthday.screens.avatarPicker.camera.navigateToCamera
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
@OptIn(ExperimentalPermissionsApi::class)
fun Content(onDismissRequest: () -> Unit, onPermissionGranted: () -> Unit) {
    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)
    if (permissionState.status.isGranted) {
        onPermissionGranted()
        return
    }
    CameraPermissionDialog(onDismissRequest, { permissionState.launchPermissionRequest() })
}

fun NavController.navigateToCameraPermission() {
    popBackStack()
    navigate(CameraPermissionRoute)
}

fun NavController.onPermissionGranted() {
    navigateToCamera()
}