package com.niklas.ux62550.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Color_background = Color(0xFF393939)

val Color_container = Color(0xFFF3EDF7)

val SearchColorForText = Color(0xFF958E9F)
val DescriptionColor = Color(0xFFB2B2B2)
val AwardAndDetailRating = Color(0xFFE0E0E0)

val ReviewColor = Color(0xFFF1D338)
val TextFieldColor = Color(0xFFD9D9D9)

val placeholderIconColor = Color(0xFFD9D9D9)
val starYellow = Color(0xFFF1D338)
val ProfileBtnRed = Color(0xFFFF1D21)

val TextFieldDescColor = Color(0xFFB9B9B9)

val RegisterButtonBlue = Color(0xFF1D9DFF)
val LoginButtonGray = Color(0xFF656565)

val RedColorGradient = arrayOf(
    0.0f to colorAlphaPctRGC(0x00FF0000, 75f),
    0.75f to colorAlphaPctRGC(0x00B50000, 40f),
    0.95f to colorAlphaPctRGC(0x008F0000, 20f),
    1f to colorAlphaPctRGC(0x006A0000, 0f)
)

private fun colorAlphaPctRGC(color: Int, pct: Float): Color {
    val a = (pct/100*255).toInt() shl 3*8
    return Color(color + a)
}
