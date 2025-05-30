package com.vladislaviliev.birthday.screens.kid

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vladislaviliev.birthday.ActivityViewModel
import com.vladislaviliev.birthday.networking.State
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
    val message = (state as? State.Connected)?.message
    if (null == message) {
        onDisconnect()
        return
    }
    val avatarBitmap =
        hiltViewModel<ActivityViewModel>(LocalContext.current as ViewModelStoreOwner)
            .state
            .collectAsStateWithLifecycle()
            .value
    KidScreen(message, avatarBitmap, onAvatarPickerClick)
}

fun NavController.onDisconnected() {
    navigate(ConnectScreenRoute) {
        popUpTo(KidScreenRoute) { inclusive = true }
    }
}