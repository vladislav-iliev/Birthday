package com.vladislaviliev.birthday.screens.avatarPicker.gallery

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object GalleryRoute

fun NavGraphBuilder.addGalleryDestination(onSelected: () -> Unit) {
    composable<GalleryRoute> { Content(onSelected) }
}

@Composable
fun Content(onDone: () -> Unit) {
    val vm = hiltViewModel<GalleryViewModel>()
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        if (null != it) vm.copyAvatarFromUri(it)
        onDone()
    }
    LaunchedEffect(true) { launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }
}

fun NavController.navigateToGallery() {
    popBackStack()
    navigate(GalleryRoute)
}