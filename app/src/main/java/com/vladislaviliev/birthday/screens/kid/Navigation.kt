package com.vladislaviliev.birthday.screens.kid

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vladislaviliev.birthday.screens.connect.ConnectScreenRoute
import kotlinx.serialization.Serializable

@Serializable
data object KidScreenRoute

fun NavGraphBuilder.addKidScreenDestination(onDisconnect: () -> Unit, onAvatarPickerClick: () -> Unit) {
    composable<KidScreenRoute> { Content(onDisconnect, onAvatarPickerClick) }
}

@Composable
private fun Content(onDisconnect: () -> Unit, onAvatarPickerClick: () -> Unit) {
    val vm = hiltViewModel<ViewModel>()
    val state by vm.state.collectAsStateWithLifecycle()
    if (!state.isActive) {
        onDisconnect()
        return
    }
    KidScreen(state, onAvatarPickerClick)
}

fun NavController.onDisconnected() {
    navigate(ConnectScreenRoute) {
        popUpTo(KidScreenRoute) { inclusive = true }
    }
}