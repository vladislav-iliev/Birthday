package com.vladislaviliev.birthday.screens.avatarPicker.camera.permission

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.vladislaviliev.birthday.R

@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun CameraPermissionDialog(
    onDismissRequest: () -> Unit,
    onRequestClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        dismissButton = { Button(onDismissRequest) { Text(stringResource(android.R.string.cancel)) } },
        confirmButton = { Button(onRequestClicked) { Text(stringResource(R.string.request_permission)) } },
        title = { Text(stringResource(R.string.permission_needed)) },
        text = { Text(stringResource(R.string.using_camera_as_source_requires_permission)) },
        icon = { Icon(Icons.Default.Info, null) },
    )
}

@Preview
@Composable
fun PermissionDialogPreview() {
    CameraPermissionDialog({}, {})
}