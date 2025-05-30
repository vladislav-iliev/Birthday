package com.vladislaviliev.birthday.avatarPicker

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.vladislaviliev.birthday.avatarPicker.camera.addCameraPermissionRequestDestination
import com.vladislaviliev.birthday.avatarPicker.camera.navigateToCameraPermission
import com.vladislaviliev.birthday.avatarPicker.camera.onPermissionGranted
import com.vladislaviliev.birthday.avatarPicker.chooseSource.ChooseSourceRoute
import com.vladislaviliev.birthday.avatarPicker.chooseSource.addChooseSourceDestination
import com.vladislaviliev.birthday.avatarPicker.gallery.addGalleryPickerDestination
import com.vladislaviliev.birthday.avatarPicker.gallery.navigateToGalleryPicker
import com.vladislaviliev.birthday.avatarPicker.gallery.onImageSelected
import kotlinx.serialization.Serializable

@Serializable
object AvatarPickerNavGraphRoute

fun NavGraphBuilder.addAvatarPickerGraph(controller: NavController) {
    navigation<AvatarPickerNavGraphRoute>(ChooseSourceRoute) { addDestinations(controller) }
}

private fun NavGraphBuilder.addDestinations(controller: NavController) {
    addChooseSourceDestination(
        controller::popBackStack,
        controller::navigateToGalleryPicker,
        controller::navigateToCameraPermission,
    )
    addGalleryPickerDestination(controller::onImageSelected)
    addCameraPermissionRequestDestination(controller::popBackStack, controller::onPermissionGranted)
}

fun NavController.navigateToAvatarPicker() = navigate(AvatarPickerNavGraphRoute)