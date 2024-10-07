package com.niklas.ux62550

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

/*
  * Used to convert from pixels used in Figma on a 360x800px screen to dp units
 */
@Composable
fun ConvertPxToDp(px: Float): Float {
    //val density = LocalDensity.current.density // 2.625 = Get current screen density (pixels per dp)
    val widthDp = LocalConfiguration.current.screenWidthDp.toFloat(); // 411

    return px * (360f/widthDp)
}

fun measureTextWidth(text: String, fontSize: Float): Float {
    return text.length * (fontSize * 0.5f) // Simplified estimation of text width
}

fun measureTextHeight(text: String, fontSize: Float): Float {
    return fontSize // For simplicity, return fontSize as height
}
