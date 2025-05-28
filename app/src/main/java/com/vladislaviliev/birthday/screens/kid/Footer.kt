package com.vladislaviliev.birthday.screens.kid

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vladislaviliev.birthday.R

@Composable
fun Footer(modifier: Modifier = Modifier) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(15.ptToDp))
        Image(painterResource(R.drawable.logo), stringResource(R.string.nanit), Modifier.width(66.dp))
//        Spacer(Modifier.height(53.ptToDp))
//        ShareButton({})
//        Spacer(Modifier.height(53.ptToDp))
    }
}