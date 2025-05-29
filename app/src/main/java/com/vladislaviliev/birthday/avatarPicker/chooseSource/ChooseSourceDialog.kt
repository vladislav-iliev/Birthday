package com.vladislaviliev.birthday.avatarPicker.chooseSource

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import com.vladislaviliev.birthday.R

enum class AvatarSource { GALLERY, CAMERA }

@Composable
fun ChooseSourceDialog(
    onDismissRequest: () -> Unit,
    onGalleryPick: () -> Unit,
    onCameraPick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedSource by rememberSaveable { mutableStateOf(AvatarSource.GALLERY) }
    val onItemSelected = { item: AvatarSource -> selectedSource = item }

    val onConfirmClick = when (selectedSource) {
        AvatarSource.GALLERY -> onGalleryPick
        AvatarSource.CAMERA -> onCameraPick
    }

    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        icon = { Icon(Icons.Default.Face, null) },
        title = { Text(stringResource(R.string.choose_avatar_source)) },
        text = { Items(selectedSource, onItemSelected) },
        confirmButton = { Button(onConfirmClick) { Text(stringResource(android.R.string.ok)) } },
        dismissButton = { Button(onDismissRequest) { Text(stringResource(android.R.string.cancel)) } },
    )
}

@Composable
private fun Items(selected: AvatarSource, onItemSelected: (AvatarSource) -> Unit, modifier: Modifier = Modifier) {
    Column(modifier) {
        Item(AvatarSource.GALLERY, selected == AvatarSource.GALLERY, onItemSelected)
        Item(AvatarSource.CAMERA, selected == AvatarSource.CAMERA, onItemSelected)
    }
}

@Composable
private fun Item(
    type: AvatarSource,
    isSelected: Boolean,
    onSelected: (AvatarSource) -> Unit,
    modifier: Modifier = Modifier
) {
    @StringRes val stringRes = when (type) {
        AvatarSource.GALLERY -> R.string.gallery
        AvatarSource.CAMERA -> R.string.camera
    }
    ListItem(
        { Text(stringResource(stringRes)) },
        modifier.selectable(isSelected, role = Role.RadioButton) { onSelected(type) },
        leadingContent = { RadioButton(isSelected, null) },
        colors = ListItemDefaults.colors(Color.Transparent)
    )
}

@Preview
@Composable
fun PreviewChooseSourceDialog() {
    ChooseSourceDialog({}, {}, {}, Modifier.fillMaxSize())
}