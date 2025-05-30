package com.vladislaviliev.birthday.avatarPicker

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.vladislaviliev.birthday.avatarPicker.cameraPermission.addCameraPermissionRequestDestination
import com.vladislaviliev.birthday.avatarPicker.cameraPermission.navigateToCameraPermission
import com.vladislaviliev.birthday.avatarPicker.cameraPermission.onPermissionGranted
import com.vladislaviliev.birthday.avatarPicker.chooseSource.ChooseSourceRoute
import com.vladislaviliev.birthday.avatarPicker.chooseSource.addChooseSourceDestination
import com.vladislaviliev.birthday.avatarPicker.cameraPermission.addCameraPermissionRequestDestination
import com.vladislaviliev.birthday.avatarPicker.cameraPermission.navigateToCameraPermission
import kotlinx.serialization.Serializable

@Serializable
object AvatarPickerNavGraphRoute

fun NavGraphBuilder.addAvatarPickerGraph(controller: NavController) {
    navigation<AvatarPickerNavGraphRoute>(ChooseSourceRoute) { addDestinations(controller) }
}

private fun NavGraphBuilder.addDestinations(controller: NavController) {
    addChooseSourceDestination(
        controller::popBackStack,
        {},
        controller::navigateToCameraPermission,
    )
    addCameraPermissionRequestDestination(controller::popBackStack, controller::onPermissionGranted)
}

fun NavController.navigateToAvatarPicker() = navigate(AvatarPickerNavGraphRoute)