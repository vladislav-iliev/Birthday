package com.vladislaviliev.birthday.screens.avatarPicker.camera

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
private object CameraDestinationRoute

fun NavGraphBuilder.addCameraDestination(onFinished: () -> Unit) {
    composable<CameraDestinationRoute> { Content(onFinished) }
}

@Composable
private fun Content(onFinished: () -> Unit) {
    val vm = hiltViewModel<CameraViewModel>()
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (Activity.RESULT_OK == it.resultCode) vm.onPhotoCopied()
        onFinished()
    }
    LaunchedEffect(true) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, vm.avatarUri)
        launcher.launch(intent)
    }
}

fun NavController.navigateToCamera() {
    popBackStack()
    navigate(CameraDestinationRoute)
}