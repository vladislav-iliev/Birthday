package com.vladislaviliev.birthday.screens.kid

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vladislaviliev.birthday.networking.State
import com.vladislaviliev.birthday.screens.connect.ConnectScreenRoute
import kotlinx.serialization.Serializable

@Serializable
data object KidScreenRoute

fun NavGraphBuilder.addKidScreenDestination(onDisconnected: () -> Unit) {
    composable<KidScreenRoute> { Content(onDisconnected) }
}

@Composable
private fun Content(onDisconnect: () -> Unit) {
    val vm = hiltViewModel<ViewModel>()
    val state by vm.state.collectAsStateWithLifecycle()
    val message = (state as? State.Connected)?.message
    if (null == message) {
        onDisconnect()
        return
    }
    KidScreen(message)
}

fun NavController.onDisconnected() {
    navigate(ConnectScreenRoute) {
        popUpTo(KidScreenRoute) { inclusive = true }
    }
}