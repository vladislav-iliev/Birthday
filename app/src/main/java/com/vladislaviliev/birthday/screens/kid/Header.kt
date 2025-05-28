package com.vladislaviliev.birthday.screens.kid

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vladislaviliev.birthday.R
import com.vladislaviliev.birthday.kids.AgeCalculator

@Composable
fun Header(kidName: String, ageMonths: Int, modifier: Modifier = Modifier) {
    val years = AgeCalculator().monthsToYears(ageMonths)
    val showYears = 1 <= years
    val number = if (showYears) years else ageMonths
    val durationWord =
        if (showYears) pluralStringResource(R.plurals.plurals_year, years)
        else pluralStringResource(R.plurals.plurals_month, ageMonths)

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        HeaderText(stringResource(R.string.today_x_is, kidName))
        Spacer(Modifier.height(13.dp))
        DrawnNumber(number)
        Spacer(Modifier.height(14.dp))
        HeaderText(stringResource(R.string.x_old, durationWord))
    }
}

@Composable
fun HeaderText(text: String, modifier: Modifier = Modifier) {
    Text(
        text.uppercase(),
        modifier,
        textAlign = TextAlign.Center,
        color = colorResource(R.color.color_394562),
        fontSize = 21.sp,
    )
}

@DrawableRes
fun numberToDrawable(number: Int) = when (number) {
    1 -> R.drawable.number_1
    2 -> R.drawable.number_2
    3 -> R.drawable.number_3
    4 -> R.drawable.number_4
    5 -> R.drawable.number_5
    6 -> R.drawable.number_6
    7 -> R.drawable.number_7
    8 -> R.drawable.number_8
    9 -> R.drawable.number_9
    10 -> R.drawable.number_10
    11 -> R.drawable.number_11
    12 -> R.drawable.number_12
    else -> throw IllegalArgumentException("No drawable for number $number")
}

@Composable
fun DrawnNumber(number: Int, modifier: Modifier = Modifier) {
    Row(modifier, Arrangement.Center, Alignment.CenterVertically) {
        Image(painterResource(R.drawable.swirls_left), null)
        Spacer(Modifier.width(22.dp))
        Image(painterResource(numberToDrawable(number)), number.toString())
        Spacer(Modifier.width(22.dp))
        Image(painterResource(R.drawable.swirls_right), null)
    }
}