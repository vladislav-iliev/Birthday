package com.vladislaviliev.birthday.avatarPicker.camera

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vladislaviliev.birthday.avatarPicker.AvatarSaver
import kotlinx.serialization.Serializable

@Serializable
object CameraDestinationRoute

fun NavGraphBuilder.addCameraDestination(onPhotoCaptured: (Uri) -> Unit) {
    composable<CameraDestinationRoute> { Content(onPhotoCaptured) }
}

@Composable
private fun Content(onPhotoCaptured: (Uri) -> Unit) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val resultOk = Activity.RESULT_OK == it.resultCode
        onPhotoCaptured(if (resultOk) AvatarSaver(context).fileUri else Uri.EMPTY)
    }

    LaunchedEffect(true) {
        launcher.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
    }
}

fun NavController.navigateToCamera() {
    popBackStack()
    navigate(CameraDestinationRoute)
}

fun NavController.onPhotoCaptured(uri: Uri) {
    popBackStack()
}