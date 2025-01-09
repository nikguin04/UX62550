package com.niklas.ux62550.ui.feature.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.niklas.ux62550.R
import com.niklas.ux62550.data.remote.RemoteMediaDataSource.Companion.BASE_IMAGE_URL

enum class ImageSize {
    BACKDROP, LOGO, POSTER, PROFILE, STILL
}

@Composable
fun MediaItem(uri: String?, round: Dp = 0.dp, modifier: Modifier = Modifier, size: ImageSize) {
    val sizeStr = when (size) {
        ImageSize.BACKDROP -> "w1280"
        ImageSize.LOGO -> "w500"
        ImageSize.POSTER -> "w780"
        ImageSize.PROFILE -> "w185"
        ImageSize.STILL -> "w300"
    }
    val imguri = if (uri!=null) "$BASE_IMAGE_URL$sizeStr/$uri" else "https://cataas.com/cat"
    AsyncImage(
        model = imguri,
        contentDescription = null, // TODO: include content description
        error = debugPlaceholder(R.drawable.howlsmovingcastle_en),
        modifier = modifier
            .clip(RoundedCornerShape(round))
    )
}
@Composable
fun debugPlaceholder(@DrawableRes debugPreview: Int) =
    if (LocalInspectionMode.current) {
        painterResource(id = debugPreview) // Source for preview
    } else {
        painterResource(id = R.drawable.networkerror)
        //null // Source for build application
    }