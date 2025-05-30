package com.vladislaviliev.birthday.avatarPicker.gallery

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import kotlinx.serialization.Serializable

@Serializable
object GalleryPickerRoute

fun NavGraphBuilder.addGalleryPickerDestination(onImageSelected: () -> Unit) {
    dialog<GalleryPickerRoute> { Content(onImageSelected) }
}

@Composable
fun Content(onImageSelected: () -> Unit) {

}

fun NavController.navigateToGalleryPicker() {
    popBackStack()
    navigate(GalleryPickerRoute)
}

fun NavController.onImageSelected() {
    popBackStack()
}