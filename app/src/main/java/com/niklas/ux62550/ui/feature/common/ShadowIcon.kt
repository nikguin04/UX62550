package com.niklas.ux62550.ui.feature.common

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.S
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun ShadowIconPreview() {
    ShadowIcon(Icons.AutoMirrored.Filled.ArrowBack, null)
}

@Composable
fun ShadowIcon(imageVector: ImageVector, contentDescription: String?, modifier: Modifier = Modifier) {
    // This is a filthy workaround because Compose doesn't support non-rectangular drop shadows
    // Note that the blur modifier only works on Android 12+, so it doesn't look as good on older phones
    val tint = if (SDK_INT >= S) Color.Black else Color.Black.copy(alpha = 0.25f)
    Icon(
        imageVector = imageVector,
        contentDescription = null,
        modifier = modifier.offset(y = 2.dp).blur(2.dp), // Offset and blur to create a drop shadow appearance
        tint = tint // Dark tint, transparent if blur doesn't work
    )
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier
    )
}
