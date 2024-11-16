package com.niklas.ux62550.ui.feature.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.niklas.ux62550.R

@Composable
fun LogoBox(modifier: Modifier = Modifier, size: Dp) {
    // Compose Box with the image and 5% rounded corners
    Box(modifier = modifier.size(size).clip(RoundedCornerShape(5))) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(size) // Same size for the image
                .clip(RoundedCornerShape(0.00f * size.value)),
            contentScale = ContentScale.Crop // Crops the image to fit the box
        )
    }
}
