package com.vladislaviliev.birthday.avatarPicker.camera

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vladislaviliev.birthday.ActivityViewModel
import kotlinx.serialization.Serializable

@Serializable
object CameraDestinationRoute

fun NavGraphBuilder.addCameraDestination(onFinished: () -> Unit) {
    composable<CameraDestinationRoute> { Content(onFinished) }
}

@Composable
private fun Content(onFinished: () -> Unit) {
    val context = LocalContext.current
    val activityVm = hiltViewModel<ActivityViewModel>(context as ViewModelStoreOwner)

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (Activity.RESULT_OK == it.resultCode) activityVm.onPhotoCopied()
        onFinished()
    }

    LaunchedEffect(true) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, activityVm.fileUri)
        launcher.launch(intent)
    }
}

fun NavController.navigateToCamera() {
    popBackStack()
    navigate(CameraDestinationRoute)
}