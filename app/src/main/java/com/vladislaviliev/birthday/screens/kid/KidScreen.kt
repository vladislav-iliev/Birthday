package com.vladislaviliev.birthday.screens.kid

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.vladislaviliev.birthday.R

private const val backgroundRef = "background"
private const val topSpacerRef = "top_spacer"
private const val headerRef = "header"
private const val middleSpacerRef = "middle_spacer"
private const val avatarRef = "avatar"
private const val bottomSpacerRef = "bottom_spacer"
private const val footerRef = "footer"

@Composable
fun KidScreen(modifier: Modifier = Modifier) {

    ConstraintLayout(
        constraints(), modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Image(
            painter = painterResource(R.drawable.background_pelican),
            null,
            Modifier
                .layoutId(backgroundRef)
                .zIndex(2f),
            contentScale = ContentScale.Crop,
        )
        Spacer(
            Modifier
                .layoutId(topSpacerRef)
                .zIndex(10f),
        )
        Header(
            "Johny Doe His Name Is So Long Lol", 16, Modifier
                .layoutId(headerRef)
                .zIndex(3f)
        )
        Spacer(
            Modifier
                .layoutId(middleSpacerRef)
                .zIndex(10f)
        )
        Avatar(
            Modifier
                .layoutId(avatarRef)
                .zIndex(1f)
        )
        Image(
            painterResource(R.drawable.logo),
            null,
            Modifier
                .width(66.dp)
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

private fun constraints() = ConstraintSet {
    val background = createRefFor(backgroundRef)
    val topSpacer = createRefFor(topSpacerRef)
    val header = createRefFor(headerRef)
    val middleSpacer = createRefFor(middleSpacerRef)
    val avatar = createRefFor(avatarRef)
    val footer = createRefFor(footerRef)
    val bottomSpacer = createRefFor(bottomSpacerRef)

    val startGuideline = createGuidelineFromStart(50.dp)
    val endGuideline = createGuidelineFromEnd(50.dp)

    constrain(background) {
        width = Dimension.wrapContent
        height = Dimension.wrapContent
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
        verticalChainWeight = 1f
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
        verticalChainWeight = 1f
    }

    constrain(avatar) {
        width = Dimension.ratio("1:1")
        height = Dimension.fillToConstraints
        top.linkTo(middleSpacer.bottom)
        start.linkTo(startGuideline)
        end.linkTo(endGuideline)
        bottom.linkTo(footer.top)
        verticalChainWeight = 6f
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
        verticalChainWeight = 3f
    }

    createVerticalChain(
        topSpacer,
        header.withChainParams(topMargin = 20.dp, bottomMargin = 15.dp),
        middleSpacer,
        avatar,
        footer.withChainParams(topMargin = 15.dp),
        bottomSpacer,
        chainStyle = ChainStyle.Spread
    )
}

@Preview(showBackground = true, showSystemUi = true, device = "spec:width=411dp,height=891dp,orientation=portrait")
@Composable
fun KidScreenPreview() {
    KidScreen(Modifier.fillMaxSize())
}