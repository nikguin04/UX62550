package com.niklas.ux62550.models

import android.media.Image
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

data class MediaItem  (
    val name: String,
    @DrawableRes val backdrop: Int,
    val tempColor: Color
) {

}