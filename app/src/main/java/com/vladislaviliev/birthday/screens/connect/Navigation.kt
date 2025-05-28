package com.vladislaviliev.birthday.screens.connect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vladislaviliev.birthday.networking.getMessageOrNull
import com.vladislaviliev.birthday.screens.kid.KidScreenRoute
import kotlinx.serialization.Serializable

@Serializable
object ConnectScreenRoute

fun NavGraphBuilder.addConnectScreenDestination(onConnected: () -> Unit) {
    composable<ConnectScreenRoute> { Content(onConnected) }
}

@Composable
private fun Content(onConnected: () -> Unit) {
    val viewModel = hiltViewModel<ViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    if (null != state.getMessageOrNull()) {
        onConnected()
        return
    }
    val connect = { ip: String, port: Int -> viewModel.connect(ip, port) }
    ConnectScreen(connect, state)
}

fun NavController.onConnected() {
    navigate(KidScreenRoute) {
        popUpTo(ConnectScreenRoute) { inclusive = true }
    }
}