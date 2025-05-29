package com.vladislaviliev.birthday.avatarPicker

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.vladislaviliev.birthday.avatarPicker.chooseSource.ChooseSourceRoute
import com.vladislaviliev.birthday.avatarPicker.chooseSource.addChooseSourceDestination
import kotlinx.serialization.Serializable

@Serializable
object AvatarPickerNavGraphRoute

fun NavGraphBuilder.addAvatarPickerGraph(controller: NavController) {
    navigation<AvatarPickerNavGraphRoute>(ChooseSourceRoute) { addDestinations(controller) }
}

private fun NavGraphBuilder.addDestinations(controller: NavController) {
    addChooseSourceDestination(controller::popBackStack, {}, {})
}

fun NavController.navigateToAvatarPicker() = navigate(AvatarPickerNavGraphRoute)