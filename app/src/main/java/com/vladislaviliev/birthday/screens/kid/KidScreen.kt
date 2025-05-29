package com.vladislaviliev.birthday.screens.kid

import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.vladislaviliev.birthday.R
import com.vladislaviliev.birthday.Theme
import com.vladislaviliev.birthday.networking.Message

private const val backgroundRef = "background"
private const val topSpacerRef = "top_spacer"
private const val headerRef = "header"
private const val middleSpacerRef = "middle_spacer"
private const val avatarRef = "avatar"
private const val bottomSpacerRef = "bottom_spacer"
private const val footerRef = "footer"

@Composable
fun KidScreen(message: Message, onAvatarPickerClick: () -> Unit, modifier: Modifier = Modifier) {
    ConstraintLayout(
        constraints(LocalContext.current.resources.displayMetrics),
        modifier
            .fillMaxSize()
            .background(colorResource(message.theme.backgroundColorRes))
            .statusBarsPadding()
    ) {
        Image(
            painter = painterResource(message.theme.backgroundDrawableRes),
            null,
            Modifier
                .layoutId(backgroundRef)
                .zIndex(2f),
            contentScale = ContentScale.Crop,
            /* TODO: verify alignment requirement with examiners, bottom-centered hides the items behind drawable
                alignment = Alignment.BottomCenter */
        )
        Spacer(
            Modifier
                .layoutId(topSpacerRef)
                .zIndex(10f),
        )
        Header(
            message.name,
            message.ageMonths,
            Modifier
                .layoutId(headerRef)
                .zIndex(3f)
        )
        Spacer(
            Modifier
                .layoutId(middleSpacerRef)
                .zIndex(10f)
        )
        Avatar(
            message,
            onAvatarPickerClick,
            Modifier
                .layoutId(avatarRef)
                .zIndex(1f)
        )
        Image(
            painterResource(R.drawable.logo),
            stringResource(R.string.nanit),
            Modifier
                .layoutId(footerRef)
                .zIndex(3f)
        )
        Spacer(
            Modifier
                .navigationBarsPadding()
                .layoutId(bottomSpacerRef)
                .zIndex(10f),
        )
    }
}

private fun constraints(displayMetrics: DisplayMetrics) = ConstraintSet {
    val background = createRefFor(backgroundRef)
    val topSpacer = createRefFor(topSpacerRef)
    val header = createRefFor(headerRef)
    val middleSpacer = createRefFor(middleSpacerRef)
    val avatar = createRefFor(avatarRef)
    val footer = createRefFor(footerRef)
    val bottomSpacer = createRefFor(bottomSpacerRef)

    val startGuideline = createGuidelineFromStart(50.dp)
    val endGuideline = createGuidelineFromEnd(50.dp)

    /*
     * The header needs to be exactly at the center between StatusBar and Avatar
     * whenever possible.
     * The following percent will be applied to weights to compensate for the
     * difference in the default margins.
     *
     * Don't forget to ensure the sum of all weights is reasonably close to 100!
     */
    val percentOffset = 2.5.dp.asHeightPercent(displayMetrics)
    val topSpacerWeight = 12.5f - percentOffset
    val middleSpacerWeight = 12.5f + percentOffset
    val avatarWeight = 50f
    val bottomSpacerWeight = 25f

    constrain(background) {
        width = Dimension.fillToConstraints
        height = Dimension.fillToConstraints
        top.linkTo(parent.top)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        bottom.linkTo(parent.bottom)
    }

    constrain(topSpacer) {
        width = Dimension.fillToConstraints
        height = Dimension.fillToConstraints
        top.linkTo(parent.top)
        start.linkTo(startGuideline)
        end.linkTo(endGuideline)
        bottom.linkTo(header.top)
        verticalChainWeight = topSpacerWeight
    }

    constrain(header) {
        width = Dimension.fillToConstraints
        height = Dimension.wrapContent
        top.linkTo(topSpacer.bottom)
        start.linkTo(startGuideline)
        end.linkTo(endGuideline)
        bottom.linkTo(middleSpacer.top)
    }

    constrain(middleSpacer) {
        width = Dimension.fillToConstraints
        height = Dimension.fillToConstraints
        top.linkTo(header.bottom)
        start.linkTo(startGuideline)
        end.linkTo(endGuideline)
        bottom.linkTo(avatar.top)
        verticalChainWeight = middleSpacerWeight
    }

    constrain(avatar) {
        width = Dimension.ratio("1:1")
        height = Dimension.fillToConstraints
        top.linkTo(middleSpacer.bottom)
        start.linkTo(startGuideline)
        end.linkTo(endGuideline)
        bottom.linkTo(footer.top)
        verticalChainWeight = avatarWeight
    }

    constrain(footer) {
        width = Dimension.value(70.dp)
        height = Dimension.wrapContent
        top.linkTo(avatar.bottom)
        start.linkTo(startGuideline)
        end.linkTo(endGuideline)
        bottom.linkTo(bottomSpacer.top)
    }

    constrain(bottomSpacer) {
        width = Dimension.fillToConstraints
        height = Dimension.fillToConstraints
        top.linkTo(footer.bottom)
        start.linkTo(startGuideline)
        end.linkTo(endGuideline)
        bottom.linkTo(parent.bottom)
        verticalChainWeight = bottomSpacerWeight
    }

    createVerticalChain(
        topSpacer,
        header.withChainParams(topMargin = 20.dp, bottomMargin = 15.dp),
        middleSpacer,
        avatar,
        footer.withChainParams(topMargin = 15.dp),
        bottomSpacer,
        chainStyle = ChainStyle.SpreadInside
    )
}

private fun Dp.asHeightPercent(displayMetrics: DisplayMetrics): Float {
    val dpInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.value, displayMetrics)
    return 100 * dpInPx / displayMetrics.heightPixels.toFloat()
}

@Preview(showBackground = true, showSystemUi = true, device = "spec:width=411dp,height=891dp")
@Composable
fun KidScreenPreview() {
    KidScreen(Message("Johny Doe", 11, Theme.PELICAN), {}, Modifier.fillMaxSize())
}