package com.vladislaviliev.birthday.avatarPicker

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.vladislaviliev.birthday.avatarPicker.camera.addCameraDestination
import com.vladislaviliev.birthday.avatarPicker.camera.permission.addCameraPermissionDestination
import com.vladislaviliev.birthday.avatarPicker.camera.permission.navigateToCameraPermission
import com.vladislaviliev.birthday.avatarPicker.camera.permission.onPermissionGranted
import com.vladislaviliev.birthday.avatarPicker.chooseSource.ChooseSourceRoute
import com.vladislaviliev.birthday.avatarPicker.chooseSource.addChooseSourceDestination
import com.vladislaviliev.birthday.avatarPicker.gallery.addGalleryDestination
import com.vladislaviliev.birthday.avatarPicker.gallery.navigateToGallery
import kotlinx.serialization.Serializable

@Serializable
private object Route

fun NavGraphBuilder.addAvatarPickerGraph(controller: NavController) {
    navigation<Route>(ChooseSourceRoute) { addDestinations(controller) }
}

private fun NavGraphBuilder.addDestinations(controller: NavController) {
    addChooseSourceDestination(
        controller::popBackStack,
        controller::navigateToGallery,
        controller::navigateToCameraPermission,
    )
    addGalleryDestination(controller::popBackStack)
    addCameraPermissionDestination(controller::popBackStack, controller::onPermissionGranted)
    addCameraDestination(controller::popBackStack)
}

fun NavController.navigateToAvatarPicker() = navigate(Route)