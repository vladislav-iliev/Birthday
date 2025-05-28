package com.vladislaviliev.birthday.screens.connect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vladislaviliev.birthday.networking.State
import kotlinx.serialization.Serializable

@Serializable
object ScreenRoute

fun NavGraphBuilder.addScreenDestination(onConnected: () -> Unit) {
    composable<ScreenRoute> { Content(onConnected) }
}

@Composable
private fun Content(onConnected: () -> Unit) {
    val viewModel = hiltViewModel<ViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    if (canProgressToKidScreen(state)) {
        onConnected()
        return
    }
    val connect = { ip: String, port: Int -> viewModel.connect(ip, port) }
    ConnectScreen(connect, state)
}

private fun canProgressToKidScreen(state: State) = state is State.Connected && null != state.message