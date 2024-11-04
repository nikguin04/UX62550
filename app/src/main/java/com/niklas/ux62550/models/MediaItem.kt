package com.niklas.ux62550.models

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class MediaItem  (
    val name: String,
    @DrawableRes val backdrop: Int,
    val tempColor: Color
) {

}