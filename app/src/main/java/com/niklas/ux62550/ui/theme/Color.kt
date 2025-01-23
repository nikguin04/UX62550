package com.niklas.ux62550.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650A4)
val PurpleGrey40 = Color(0xFF625B71)
val Pink40 = Color(0xFF7D5260)

val Color_background = Color(0xFF393939)

val SearchColorForText = Color(0xFF958E9F)
val DescriptionColor = Color(0xFFB2B2B2)
val AwardAndDetailRating = Color(0xFFE0E0E0)

val ReviewColor = Color(0xFFF1D338)
val TextFieldColor = Color(0xFFD9D9D9)
val TextFieldDescColor = Color(0xFFB9B9B9)

val placeholderIconColor = Color(0xFFD9D9D9)
val starYellow = Color(0xFFF1D338)

val ProfileBtnRed = Color(0xFFFF1D21)
val RegisterButtonBlue = Color(0xFF1D9DFF)
val LoginButtonGray = Color(0xFF656565)

val RedColorGradient = arrayOf(
    0.00f to Color(0x00FF0000, 0.75f),
    0.75f to Color(0x00B50000, 0.40f),
    0.95f to Color(0x008F0000, 0.20f),
    1.00f to Color(0x006A0000, 0.00f)
)

private fun Color(color: Int, alpha: Float): Color {
    val a = (alpha * 255f + 0.5f).toInt() shl 24
    return Color(color or a)
}
