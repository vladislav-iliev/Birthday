package com.vladislaviliev.birthday.screens.avatarPicker.camera

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vladislaviliev.birthday.R
import kotlinx.serialization.Serializable
import java.io.File

@Serializable
private object CameraDestinationRoute

fun NavGraphBuilder.addCameraDestination(onFinished: () -> Unit) {
    composable<CameraDestinationRoute> { Content(onFinished) }
}

@Composable
fun Content(onFinished: () -> Unit) {
    val vm = hiltViewModel<CameraViewModel>()
    val context = LocalContext.current
    val tempFileUri = createTempFile(context)

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (Activity.RESULT_OK == it.resultCode) vm.copyFromUri(tempFileUri)
        deleteTempFile(context, tempFileUri)
        onFinished()
    }

    LaunchedEffect(true) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, tempFileUri)
        launcher.launch(intent)
    }
}

private fun createTempFile(context: Context): Uri {
    val file = File.createTempFile("avatar", null, context.cacheDir)
    return FileProvider.getUriForFile(context, context.getString(R.string.file_provider_authority), file)
}

private fun deleteTempFile(context: Context, uri: Uri) = File(context.cacheDir, uri.lastPathSegment!!).delete()

fun NavController.navigateToCamera() {
    popBackStack()
    navigate(CameraDestinationRoute)
}