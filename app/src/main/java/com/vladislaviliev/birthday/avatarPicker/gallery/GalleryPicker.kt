package com.vladislaviliev.birthday.avatarPicker.gallery

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
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
object GalleryPickerRoute

fun NavGraphBuilder.addGalleryPickerDestination(onSelected: () -> Unit) {
    composable<GalleryPickerRoute> { Content(onSelected) }
}

@Composable
fun Content(onSelected: () -> Unit) {
    val context = LocalContext.current
    val vm = hiltViewModel<ActivityViewModel>(context as ViewModelStoreOwner)

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        if (null != it) vm.copyFromUri(it)
        onSelected()
    }

    LaunchedEffect(true) { launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }
}

fun NavController.navigateToGalleryPicker() {
    popBackStack()
    navigate(GalleryPickerRoute)
}

fun NavController.onImageSelected() {

}