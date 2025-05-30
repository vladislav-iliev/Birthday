package com.vladislaviliev.birthday.avatarPicker.gallery

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import kotlinx.serialization.Serializable

@Serializable
object GalleryPickerRoute

fun NavGraphBuilder.addGalleryPickerDestination(onImageSelected: (Uri) -> Unit) {
    dialog<GalleryPickerRoute> { Content(onImageSelected) }
}

@Composable
fun Content(onImageSelected: (Uri) -> Unit) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        onImageSelected(it ?: Uri.EMPTY)
    }
    LaunchedEffect(true) { launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }
}

fun NavController.navigateToGalleryPicker() {
    popBackStack()
    navigate(GalleryPickerRoute)
}

fun NavController.onImageSelected(uri: Uri) {
    popBackStack()
}