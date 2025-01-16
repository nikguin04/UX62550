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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.niklas.ux62550.R
import com.niklas.ux62550.data.examples.SearchDataExamples
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.di.DataModule.BASE_IMAGE_URL
import kotlinx.coroutines.delay

enum class ImageSize {
    BACKDROP, LOGO, POSTER, PROFILE, STILL
}
val CrossfadeDuration: Int = 200;

@Composable
@Preview
fun NoMediaPreview() {
    val w = 350.dp
    val h = w/16*9

    val mediaItem = SearchDataExamples.MediaObjectExample.copy(backdrop_path = null)
    val imageViewModel: ImageViewModel = viewModel(factory = ImageViewModelFactory(mediaItem), key = (mediaItem.media_type ?: "") + mediaItem.id)
    imageViewModel.initPreview_NoFetch()

    Column {
        MediaItemBackdropIntercept(
            modifier = Modifier
                .size(w, h)
                .clip(RoundedCornerShape(6.dp)),
            mediaItem = mediaItem,
            backdropFallback = true,
            fetchEnBackdrop = true,
            imageViewModel = imageViewModel
        )
        Box(modifier = Modifier.size(10.dp))
        MediaItemBackdropIntercept(
            modifier = Modifier
                .size(w, h)
                .clip(RoundedCornerShape(6.dp)),
            mediaItem = mediaItem.copy(title = "Ridiculously long movie title here: and it just continues"),
            backdropFallback = true,
            fetchEnBackdrop = true,
            imageViewModel = imageViewModel
        )
        Box(modifier = Modifier.size(10.dp))
        MediaItemBackdropIntercept(
            modifier = Modifier
                .size(w, h)
                .clip(RoundedCornerShape(6.dp)),
            mediaItem = mediaItem.copy(title = "Ridiculously long movie title here: and it just continues, but this might simply just be too long because some directors choose long titles, lets see if it wraps properly"),
            backdropFallback = true,
            fetchEnBackdrop = true,
            imageViewModel = imageViewModel
        )
        Box(modifier = Modifier.size(10.dp))
        MediaItemBackdropIntercept(
            modifier = Modifier
                .size(w, h)
                .clip(RoundedCornerShape(6.dp)),
            mediaItem = mediaItem,
            backdropFallback = false,
            fetchEnBackdrop = true,
            imageViewModel = imageViewModel
        )
    }
}

@Composable
@Preview
fun MediaPreview() {
    val w = 350.dp
    val h = w/16*9
    val mediaItem = SearchDataExamples.MediaObjectExample

    Column {
        val imageViewModel: ImageViewModel = viewModel(factory = ImageViewModelFactory(mediaItem), key = (mediaItem.media_type ?: "") + "1")
        imageViewModel.initPreview_NoFetch()
        MediaItemBackdropIntercept(
            modifier = Modifier
                .size(w, h)
                .clip(RoundedCornerShape(6.dp)),
            mediaItem = mediaItem.copy(title = "Ridiculously long movie title here: Goes over regular backdrop"),
            backdropFallback = true,
            fetchEnBackdrop = true,
            imageViewModel = imageViewModel
        )
        Box(modifier = Modifier.size(10.dp))
        val imageViewModelData: ImageViewModel = viewModel(factory = ImageViewModelFactory(mediaItem), key = (mediaItem.media_type ?: "") + "2")
        imageViewModelData.initPreview()
        MediaItemBackdropIntercept(
            modifier = Modifier
                .size(w, h)
                .clip(RoundedCornerShape(6.dp)),
            mediaItem = mediaItem.copy(title = "Howls moving castle :)"),
            backdropFallback = true,
            fetchEnBackdrop = true,
            imageViewModel = imageViewModelData
        )
    }
}

@Composable
fun MediaItemBackdropIntercept(
    modifier: Modifier = Modifier,
    fetchEnBackdrop: Boolean,
    mediaItem: MediaObject,
    backdropFallback: Boolean = true,
    imageViewModel: ImageViewModel = viewModel(factory = ImageViewModelFactory(mediaItem), key = (mediaItem.media_type ?: "") + mediaItem.id)
) {
    if (fetchEnBackdrop) {
        //if (mediaItem.media_type == null) {throw Exception("Media type unknown, cant fetch english backdrop")}
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
                        uri = it.file_path,
                        modifier = modifier,
                        size = ImageSize.BACKDROP,
                        animationProgress = animationProgress
                    )
                } ?: // ELSE
                Box(modifier = modifier) // Red color is to indicate that the media has no english backdrop, this box is TEMPORARY! and for later debugging purposes when making title over media with no english backdrop
                {
                    MediaItemBackdropFallback(
                        media = mediaItem,
                        modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(6.dp)),
                        size = ImageSize.BACKDROP,
                        backdropFallback = backdropFallback,
                        animationProgress = animationProgress
                    )
                    //Box(modifier = Modifier.size(8.dp).background(Color.Red))
                }
            }

            else -> {}
        }
    } else {
        MediaItem (
            uri = mediaItem.backdrop_path,
            modifier = modifier.clip(RoundedCornerShape(6.dp)),
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
fun MediaItemBackdropFallback(
    media: MediaObject,
    modifier: Modifier = Modifier,
    size: ImageSize,
    backdropFallback: Boolean,
    animationProgress: State<Float> = getMediaItemAnimationProgress()
) {
    Box (modifier = modifier) {
        Box {
            MediaItem(
                uri = media.backdrop_path,
                modifier = modifier,
                size = size,
                animationProgress = animationProgress
            )
            Box(modifier = modifier.background(Color(0.5f, 0.5f, 0.5f, 0.5f)))
        }
        if (backdropFallback) {
            Box (
                modifier = Modifier
                    .fillMaxSize(0.9f)
                    .align(Alignment.Center)
            ) { // Wrapping box is needed to text aligns correctly vertically
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Black,
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        shadow = Shadow(
                            color = Color.DarkGray,
                            offset = Offset(4.0f, 8.0f),
                            blurRadius = 10f
                        )
                    ),
                    overflow = TextOverflow.Ellipsis,
                    text = media.title
                )
            }
        }
    }
}

@Composable
fun MediaItem(
    uri: String?,
    modifier: Modifier = Modifier,
    size: ImageSize,
    animationProgress: State<Float> = getMediaItemAnimationProgress()
) { // TODO: Remove round from here since it basically it a modifier
    val sizeStr = when (size) {
        ImageSize.BACKDROP -> "w1280"
        ImageSize.LOGO -> "w500"
        ImageSize.POSTER -> "w780"
        ImageSize.PROFILE -> "w185"
        ImageSize.STILL -> "w300"
    }

    var isLoading by remember { mutableStateOf(true) }
    var hasCrossFaded by remember { mutableStateOf(false) }

    uri?.let { uriNotNull ->
        val imgUri = "$BASE_IMAGE_URL$sizeStr/$uriNotNull"
        Box { // Box so that async image and the placeholder overlaps
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(imgUri).crossfade(CrossfadeDuration).build(),
                contentDescription = null, // TODO: include content description
                error = debugPlaceholder(R.drawable.howlsmovingcastle_en),
                onSuccess = { isLoading = false },
                onError = { isLoading = false },
                modifier = modifier,
            )

            LaunchedEffect(isLoading) {
                if (!isLoading) {
                    delay(CrossfadeDuration.toLong()) // Wait for crossfade to complete
                    hasCrossFaded = true // Remove the loader
                }
            }

            if (!hasCrossFaded) {
                // Circular progress indicator for loading animation
                AnimatedImagePlaceholder(modifier, animationProgress)
            }
        }
    } ?: run { // If uri is null
        Box (modifier = modifier.background(Color.Gray)) {
            Icon(
                modifier = Modifier.align(Alignment.Center).fillMaxSize(fraction = 0.75f),
                tint = Color.Black.copy(alpha = 0.25f),
                imageVector = Icons.Outlined.ImageNotSupported,
                contentDescription = "Image unavailable"
            )
        }
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