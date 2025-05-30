package com.vladislaviliev.birthday.avatarPicker

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.vladislaviliev.birthday.avatarPicker.camera.addCameraDestination
import com.vladislaviliev.birthday.avatarPicker.camera.onPhotoCaptured
import com.vladislaviliev.birthday.avatarPicker.camera.permission.addCameraPermissionDestination
import com.vladislaviliev.birthday.avatarPicker.camera.permission.navigateToCameraPermission
import com.vladislaviliev.birthday.avatarPicker.camera.permission.onPermissionGranted
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
    addCameraPermissionDestination(controller::popBackStack, controller::onPermissionGranted)
    addCameraDestination(controller::popBackStack)
}

fun NavController.navigateToAvatarPicker() = navigate(AvatarPickerNavGraphRoute)