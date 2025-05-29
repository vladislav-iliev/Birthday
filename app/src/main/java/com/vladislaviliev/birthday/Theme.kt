package com.vladislaviliev.birthday

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

enum class Theme(
    @DrawableRes val backgroundDrawableRes: Int,
    @ColorRes val backgroundColorRes: Int,
    @ColorRes val avatarBorderColorRes: Int,
    @DrawableRes val avatarPlaceholderRes: Int,
    @DrawableRes val avatarPickerRes: Int,
) {
    FOX(
        R.drawable.background_fox,
        R.color.color_FEEFCB,
        R.color.color_FEBE21,
        R.drawable.avatar_placeholder_yellow,
        R.drawable.avatar_choose_yellow,
    ),
    ELEPHANT(
        R.drawable.background_elephant,
        R.color.color_C5E8DF,
        R.color.color_6FC5AF,
        R.drawable.avatar_placeholder_green,
        R.drawable.avatar_choose_green,
    ),
    PELICAN(
        R.drawable.background_pelican,
        R.color.color_DAF1F6,
        R.color.color_8BD3E4,
        R.drawable.avatar_placeholder_blue,
        R.drawable.avatar_choose_blue,
    ),
}
