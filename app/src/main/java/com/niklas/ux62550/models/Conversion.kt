package com.niklas.ux62550.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/*
  * Used to convert from pixels used in Figma on a 360x800px screen to dp units
 */
@Composable
fun figmaPxToDp_w(px: Float): Dp {
    val widthDp = LocalConfiguration.current.screenWidthDp.toFloat() // 411
    val ret: Float = px/360f * widthDp
    return ret.dp
}
/*
  * Used to convert from pixels used in Figma on a 360x800px screen to dp units
 */
/*@Composable
fun figmaPxToDp_h(px: Float): Dp {
    val heightDp = LocalConfiguration.current.screenHeightDp.toFloat() // ??
    val ret: Float = px/800f * heightDp
    return ret.dp
}*/
// USE WIDTH CONVERSION ALWAYS
@Composable
fun figmaPxToDp_h(px: Float): Dp {
 return figmaPxToDp_w(px)
}

fun measureTextWidth(text: String, fontSize: Float): Float {
    return text.length * (fontSize * 0.5f) // Simplified estimation of text width
}

fun measureTextHeight(text: String, fontSize: Float): Float {
    return fontSize // For simplicity, return fontSize as height
}
