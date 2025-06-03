package com.vladislaviliev.birthday.screens.connect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
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

    val networkState by viewModel.networkState.collectAsStateWithLifecycle()
    val textState by viewModel.textState.collectAsStateWithLifecycle()

    if (null != textState) {
        onConnected()
        return
    }
    val connect = { ip: String, port: Int -> viewModel.connect(ip, port) }
    ConnectScreen(connect, networkState)
}

fun NavController.onConnected() {
    popBackStack()
    navigate(KidScreenRoute)
}