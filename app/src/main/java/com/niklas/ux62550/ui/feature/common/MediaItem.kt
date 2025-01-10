package com.niklas.ux62550.ui.feature.common

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.niklas.ux62550.R
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.data.remote.RemoteMediaDataSource.Companion.BASE_IMAGE_URL

enum class ImageSize {
    BACKDROP, LOGO, POSTER, PROFILE, STILL
}

@Composable
fun MediaItemBackdropIntercept(
    modifier: Modifier = Modifier,
    fetchEnBackdrop: Boolean,
    mediaItem: MediaObject
) {
    if (fetchEnBackdrop) {
        //if (mediaItem.media_type == null) {throw Exception("Media type unknown, cant fetch english backdrop")}
        val imageViewModel: ImageViewModel = viewModel(factory = ImageViewModelFactory(mediaItem), key = (mediaItem.media_type ?: "") + mediaItem.id)
        val imagesDataUIState: ImagesDataUIState = imageViewModel.imagesDataState.collectAsState().value

        val animationProgress = getMediaItemAnimationProgress()

        when (imagesDataUIState) {
            ImagesDataUIState.Empty -> {
                // TODO: CWL loading page?
                AnimatedImagePlaceholder(modifier.clip(RoundedCornerShape(6.dp)), animationProgress)
            }

            is ImagesDataUIState.Data -> {
                val enBackdrop = imagesDataUIState.media.getFirstEnBackdrop()
                enBackdrop?.let {
                    MediaItem(
                        round = 6.dp,
                        uri = it.file_path,
                        modifier = modifier,
                        size = ImageSize.BACKDROP,
                        animationProgress = animationProgress
                    )
                } ?: // ELSE
                Box(modifier = modifier) // Red color is to indicate that the media has no english backdrop, this box is TEMPORARY! and for later debugging purposes when making title over media with no english backdrop
                {
                    MediaItem(
                        round = 6.dp,
                        uri = mediaItem.backdrop_path, // TODO: catch null case here
                        modifier = Modifier.fillMaxSize(),
                        size = ImageSize.BACKDROP,
                        animationProgress = animationProgress
                    )
                    Box(modifier = Modifier.size(8.dp).background(Color.Red))
                }
            }

            else -> {}
        }
    } else {
        MediaItem(
            round = 6.dp,
            uri = mediaItem.backdrop_path,
            modifier = modifier,
            size = ImageSize.BACKDROP
        )
    }


}

@Composable
fun getMediaItemAnimationProgress(): State<Float> {
    val infiniteTransition = rememberInfiniteTransition(label = "PlaceholderAnimationTransition")
    return infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "PlaceholderAnimationProgress"
    )
}

@Composable
fun MediaItem(uri: String?, round: Dp = 0.dp, modifier: Modifier = Modifier, size: ImageSize, animationProgress: State<Float> = getMediaItemAnimationProgress()) { // TODO: Remove round from here since it basically it a modifier
    val sizeStr = when (size) {
        ImageSize.BACKDROP -> "w1280"
        ImageSize.LOGO -> "w500"
        ImageSize.POSTER -> "w780"
        ImageSize.PROFILE -> "w185"
        ImageSize.STILL -> "w300"
    }
    val imguri = if (uri!=null) "$BASE_IMAGE_URL$sizeStr/$uri" else "https://cataas.com/cat"

    var isLoading by remember { mutableStateOf(true) }

    AsyncImage(
        model = imguri,
        contentDescription = null, // TODO: include content description
        error = debugPlaceholder(R.drawable.howlsmovingcastle_en),
        onSuccess = {isLoading = false},
        onError = {isLoading = false},
        modifier = modifier.clip(RoundedCornerShape(round)),
    )
    if (isLoading) {
        // Circular progress indicator for loading animation
        AnimatedImagePlaceholder(modifier.clip(RoundedCornerShape(round)), animationProgress)
    }
}
@Composable
fun debugPlaceholder(@DrawableRes debugPreview: Int) =
    if (LocalInspectionMode.current) {
        painterResource(id = debugPreview) // Source for preview
    } else {
        painterResource(id = R.drawable.networkerror)
        //null // Source for build application
    }


@Composable
fun MediaItemPreview(round: Dp = 0.dp, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(round))
            .background(Color.Gray)
    )
}

@Composable
fun AnimatedImagePlaceholder(modifier: Modifier = Modifier, animationProgress: State<Float>) {

    // Placeholder with animated cross-fading gradients
    Canvas(modifier = modifier) {
        // Background color
        drawRect(color = Color.Gray.copy(alpha = 0.3f))

        // Horizontal gradient animation
        val horizontalOffset = size.width * (animationProgress.value) * 2

        drawRect(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color.Gray.copy(alpha = 0.0f),
                    Color.Gray.copy(alpha = 0.2f),
                    Color.Gray.copy(alpha = 0.0f)
                ),
                startX = -size.width + horizontalOffset,
                endX = horizontalOffset
            ),
            size = size
        )

        // Vertical gradient animation
        val verticalOffset = size.height * (animationProgress.value) * 2
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Gray.copy(alpha = 0.0f),
                    Color.Gray.copy(alpha = 0.2f),
                    Color.Gray.copy(alpha = 0.0f)
                ),
                startY = -size.height + verticalOffset,
                endY = verticalOffset
            ),
            size = size
        )
    }
}