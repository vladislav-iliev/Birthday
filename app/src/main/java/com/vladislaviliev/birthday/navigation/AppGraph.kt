package com.vladislaviliev.birthday.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.createGraph
import com.vladislaviliev.birthday.screens.connect.ScreenRoute
import com.vladislaviliev.birthday.screens.connect.addScreenDestination
import kotlinx.serialization.Serializable

@Serializable
object AppGraphRoute

fun createAppGraph(controller: NavController) =
    controller.createGraph(ScreenRoute, AppGraphRoute::class) { addAppGraphDestinations(controller) }

private fun NavGraphBuilder.addAppGraphDestinations(controller: NavController) {
    addScreenDestination { repeat(5) { println("Received kid") } }
}