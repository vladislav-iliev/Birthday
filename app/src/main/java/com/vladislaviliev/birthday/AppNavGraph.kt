package com.vladislaviliev.birthday

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.createGraph
import com.vladislaviliev.birthday.screens.avatarPicker.addAvatarPickerGraph
import com.vladislaviliev.birthday.screens.avatarPicker.navigateToAvatarPicker
import com.vladislaviliev.birthday.screens.connect.ConnectScreenRoute
import com.vladislaviliev.birthday.screens.connect.addConnectScreenDestination
import com.vladislaviliev.birthday.screens.connect.onConnected
import com.vladislaviliev.birthday.screens.kid.addKidScreenDestination
import com.vladislaviliev.birthday.screens.kid.onDisconnected
import kotlinx.serialization.Serializable

@Serializable
object AppGraphRoute

fun createAppGraph(controller: NavController) =
    controller.createGraph(ConnectScreenRoute, AppGraphRoute::class) { addAppGraphDestinations(controller) }

private fun NavGraphBuilder.addAppGraphDestinations(controller: NavController) {
    addAvatarPickerGraph(controller)
    addConnectScreenDestination(controller::onConnected)
    addKidScreenDestination(controller::onDisconnected, controller::navigateToAvatarPicker)
}