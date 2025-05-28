package com.vladislaviliev.birthday.screens.kid

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vladislaviliev.birthday.R

//
//
//
//  UNUSED
//
//
@Composable
fun ShareButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    val colors = ButtonDefaults.buttonColors(
        containerColor = colorResource(R.color.color_EF7B7B),
        contentColor = Color.White
    )

    Button(onClick, modifier, colors = colors) {
        Row(verticalAlignment = Alignment.Bottom) {
            Text(stringResource(R.string.share_the_news))
            Spacer(Modifier.width(5.dp))
            Icon(painterResource(R.drawable.share_arrow), null, Modifier.alignBy(FirstBaseline), Color.White)
        }
    }

}

@Preview
@Composable
fun ShareButtonPreview() {
    ShareButton({})
}