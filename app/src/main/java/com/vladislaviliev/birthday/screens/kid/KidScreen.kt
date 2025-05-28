package com.vladislaviliev.birthday.screens.kid

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBarsPadding
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
private const val footerRef = "footer"
private const val bottomSpacerRef = "bottom_spacer"

@Composable
fun KidScreen(modifier: Modifier = Modifier) {

    ConstraintLayout(constraints(), modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.background_pelican),
            null,
            Modifier
                .layoutId(backgroundRef)
                .zIndex(2f),
            contentScale = ContentScale.Crop
        )
        Spacer(
            Modifier
                .layoutId(topSpacerRef)
                .sizeIn(minHeight = 20.dp)
        )
        Header(
            "Johny DoeJohny DoeJohny DoeJohny Doe", 16, Modifier
                .layoutId(headerRef)
                .statusBarsPadding()
                .zIndex(3f)
        )
        Spacer(
            Modifier
                .layoutId(middleSpacerRef)
                .sizeIn(minHeight = 15.dp)
        )
        Avatar(
            Modifier
                .layoutId(avatarRef)
                .zIndex(1f)
        )
        Footer(
            Modifier
                .layoutId(footerRef)
                .navigationBarsPadding()
                .zIndex(3f)
        )
        Spacer(Modifier.layoutId(bottomSpacerRef))
    }
}

private fun constraints() = ConstraintSet { // Use res/layout/kid_screen for easier modification
    val backgroundRef = createRefFor(backgroundRef)
    val topSpacer = createRefFor(topSpacerRef)
    val header = createRefFor(headerRef)
    val middleSpacer = createRefFor(middleSpacerRef)
    val avatar = createRefFor(avatarRef)
    val footer = createRefFor(footerRef)
    val bottomSpacer = createRefFor(bottomSpacerRef)

    val avatarStartBarrier = createStartBarrier(avatar)
    val avatarEndBarrier = createEndBarrier(avatar)

    constrain(backgroundRef) {
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
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        bottom.linkTo(header.top)
        verticalChainWeight = 1f
    }

    constrain(header) {
        width = Dimension.fillToConstraints
        height = Dimension.wrapContent
        top.linkTo(topSpacer.bottom)
        start.linkTo(avatarStartBarrier)
        end.linkTo(avatarEndBarrier)
        bottom.linkTo(bottomSpacer.top)
    }

    constrain(middleSpacer) {
        width = Dimension.fillToConstraints
        height = Dimension.fillToConstraints
        top.linkTo(header.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        bottom.linkTo(avatar.top)
        verticalChainWeight = 1f
    }

    constrain(avatar) {
        width = Dimension.fillToConstraints
        height = Dimension.ratio("1:1")
        top.linkTo(bottomSpacer.bottom)
        start.linkTo(parent.start, 50.dp)
        end.linkTo(parent.end, 50.dp)
        bottom.linkTo(footer.top)
    }

    constrain(footer) {
        width = Dimension.wrapContent
        height = Dimension.wrapContent
        top.linkTo(avatar.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        bottom.linkTo(bottomSpacer.top)
    }

    constrain(bottomSpacer) {
        width = Dimension.fillToConstraints
        height = Dimension.fillToConstraints
        top.linkTo(footer.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        bottom.linkTo(parent.bottom)
        verticalChainWeight = 1f
    }

    createVerticalChain(topSpacer, header, middleSpacer, avatar, footer, bottomSpacer, chainStyle = ChainStyle.Packed)
}

@Preview(showBackground = true, showSystemUi = true, device = "spec:width=411dp,height=891dp,orientation=landscape")
@Composable
fun KidScreenPreview() {
    KidScreen(Modifier.fillMaxSize())
}