package com.vladislaviliev.birthday.screens.connect

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vladislaviliev.birthday.R
import com.vladislaviliev.birthday.networking.State

@Composable
fun ConnectScreen(connect: (String, Int) -> Unit, state: State, modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var ip by rememberSaveable { mutableStateOf("localhost") }
        var port by rememberSaveable { mutableStateOf("8080") }
        val isAuthorityValid = ip.isNotBlank() && null != port.toIntOrNull() && 0 < port.toInt()
        val onConnectClick = { if (isAuthorityValid) connect(ip, port.toInt()) }

        Spacer(Modifier.height(20.dp))
        Text(stringResource(R.string.connect_to_server), fontSize = 30.sp)
        Spacer(Modifier.height(10.dp))
        Inputs(ip, { ip = it }, port, { port = it }, isAuthorityValid, Modifier.fillMaxWidth())
        Spacer(Modifier.height(10.dp))
        Button(onConnectClick) { Text(stringResource(R.string.connect)) }
        Spacer(Modifier.height(10.dp))
        StateComposable(state)
    }
}

@Composable
private fun Inputs(
    ip: String,
    onIpChange: (String) -> Unit,
    port: String,
    onPortChange: (String) -> Unit,
    isAuthorityValid: Boolean,
    modifier: Modifier = Modifier
) {
    Row(modifier, Arrangement.Center, Alignment.CenterVertically) {
        Spacer(Modifier.weight(0.1f))
        TextField(ip, onIpChange, Modifier.weight(0.4f), isError = !isAuthorityValid)
        Spacer(Modifier.weight(0.1f))
        TextField(port, onPortChange, Modifier.weight(0.2f), isError = !isAuthorityValid)
        Spacer(Modifier.weight(0.1f))
    }
}

@Composable
private fun StateComposable(state: State) {
    if (state is State.Disconnected && state.cause != null) {
        Text(state.cause.javaClass.simpleName, color = Color.Red)
        return
    }
    if (state is State.Connecting) {
        CircularProgressIndicator()
        return
    }
}

@Preview(showBackground = true)
@Composable
fun ConnectScreenPreview() {
    ConnectScreen({ _, _ -> }, State.Connecting, Modifier.fillMaxSize())
}