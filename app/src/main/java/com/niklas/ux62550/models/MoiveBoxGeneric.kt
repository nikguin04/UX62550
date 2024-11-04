package com.niklas.ux62550.models

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color


data class MoiveBoxItem  (
    val name: String,
    @DrawableRes val backdrop: Int,
    val tempColor: Color,
    val genre: String,
    val rating: Float
) {
}
