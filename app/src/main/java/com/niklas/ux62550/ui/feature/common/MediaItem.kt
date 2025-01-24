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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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

const val CrossfadeDuration = 200

@Composable
@Preview
fun NoMediaPreview() {
    val mediaItem = SearchDataExamples.MediaObjectExample.copy(backdrop_path = null)
    val imageViewModel: ImageViewModel = viewModel(factory = ImageViewModelFactory(mediaItem), key = (mediaItem.media_type ?: "") + mediaItem.id)
    imageViewModel.initPreview_NoFetch()

    val width = 350.dp

    Column {
        MediaItemBackdropIntercept(
            fetchEnBackdrop = true,
            mediaItem = mediaItem,
            imageViewModel = imageViewModel,
            modifier = Modifier
                .width(width)
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(6.dp))
        )
        Spacer(Modifier.height(10.dp))
        MediaItemBackdropIntercept(
            fetchEnBackdrop = true,
            mediaItem = mediaItem.copy(title = "Ridiculously long movie title here: and it just continues"),
            imageViewModel = imageViewModel,
            modifier = Modifier
                .width(width)
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(6.dp))
        )
        Spacer(Modifier.height(10.dp))
        MediaItemBackdropIntercept(
            fetchEnBackdrop = true,
            mediaItem = mediaItem.copy(title = "Ridiculously long movie title here: and it just continues, but this might simply just be too long because some directors choose long titles, lets see if it wraps properly"),
            imageViewModel = imageViewModel,
            modifier = Modifier
                .width(width)
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(6.dp))
        )
        Spacer(Modifier.height(10.dp))
        MediaItemBackdropIntercept(
            fetchEnBackdrop = true,
            mediaItem = mediaItem,
            backdropFallback = false,
            imageViewModel = imageViewModel,
            modifier = Modifier
                .width(width)
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(6.dp))
        )
    }
}

@Composable
@Preview
fun MediaPreview() {
    val mediaItem = SearchDataExamples.MediaObjectExample
    val width = 350.dp

    Column {
        val imageViewModel: ImageViewModel = viewModel(factory = ImageViewModelFactory(mediaItem), key = (mediaItem.media_type ?: "") + "1")
        imageViewModel.initPreview_NoFetch()
        MediaItemBackdropIntercept(
            fetchEnBackdrop = true,
            mediaItem = mediaItem.copy(title = "Ridiculously long movie title here: Goes over regular backdrop"),
            imageViewModel = imageViewModel,
            modifier = Modifier
                .width(width)
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(6.dp))
        )
        Spacer(Modifier.height(10.dp))
        val imageViewModelData: ImageViewModel = viewModel(factory = ImageViewModelFactory(mediaItem), key = (mediaItem.media_type ?: "") + "2")
        imageViewModelData.initPreview()
        MediaItemBackdropIntercept(
            fetchEnBackdrop = true,
            mediaItem = mediaItem.copy(title = "Howls moving castle :)"),
            imageViewModel = imageViewModelData,
            modifier = Modifier
                .width(width)
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(6.dp))
        )
    }
}

@Composable
fun MediaItemBackdropIntercept(
    fetchEnBackdrop: Boolean,
    mediaItem: MediaObject,
    backdropFallback: Boolean = true,
    modifier: Modifier = Modifier,
    imageViewModel: ImageViewModel = viewModel(factory = ImageViewModelFactory(mediaItem), key = mediaItem.getUniqueStringIdentifier())
) {
    if (fetchEnBackdrop) {
        //if (mediaItem.media_type == null) {throw Exception("Media type unknown, cant fetch english backdrop")}
        val imagesDataUIState: ImagesDataUIState = imageViewModel.imagesDataState.collectAsState().value

        val animationProgress = getMediaItemAnimationProgress()

        when (imagesDataUIState) {
            is ImagesDataUIState.Empty -> {
                // TODO: CWL loading page?
                AnimatedImagePlaceholder(
                    animationProgress = animationProgress,
                    modifier = modifier.clip(RoundedCornerShape(6.dp))
                )
            }
            is ImagesDataUIState.Data -> {
                val enBackdrop = imagesDataUIState.media.getFirstEnBackdrop()
                enBackdrop?.let {
                    MediaItem(
                        uri = it.file_path,
                        size = ImageSize.BACKDROP,
                        animationProgress = animationProgress,
                        modifier = modifier
                    )
                } ?: run {
                    MediaItemBackdropFallback(
                        media = mediaItem,
                        size = ImageSize.BACKDROP,
                        backdropFallback = backdropFallback,
                        animationProgress = animationProgress,
                        modifier = modifier.clip(RoundedCornerShape(6.dp))
                    )
                }
            }
            is ImagesDataUIState.Error -> {
                Text("Network error")
            }
        }
    } else {
        MediaItem(
            uri = mediaItem.backdrop_path,
            size = ImageSize.BACKDROP,
            modifier = modifier.clip(RoundedCornerShape(6.dp))
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
        ),
        label = "PlaceholderAnimationProgress"
    )
}

@Composable
fun MediaItemBackdropFallback(
    media: MediaObject,
    size: ImageSize,
    backdropFallback: Boolean,
    animationProgress: State<Float> = getMediaItemAnimationProgress(),
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        MediaItem(
            uri = media.backdrop_path,
            size = size,
            animationProgress = animationProgress,
            modifier = modifier
        )

        if (backdropFallback) {
            Box(modifier = modifier.background(Color(0.5f, 0.5f, 0.5f, 0.5f)))
            Box(
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
    size: ImageSize,
    animationProgress: State<Float> = getMediaItemAnimationProgress(),
    modifier: Modifier = Modifier
) {
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
                AnimatedImagePlaceholder(animationProgress, modifier)
            }
        }
    } ?: run { // If uri is null
        Box(modifier = modifier.background(Color.Gray)) {
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize(fraction = 0.75f),
                tint = Color.Black.copy(alpha = 0.25f),
                imageVector = Icons.Outlined.ImageNotSupported,
                contentDescription = "Image unavailable"
            )
        }
    }
}

@Composable
fun debugPlaceholder(@DrawableRes debugPreview: Int) = painterResource(
    if (LocalInspectionMode.current) {
        debugPreview // Source for preview
    } else {
        R.drawable.networkerror // Source for build application
    }
)

@Composable
fun AnimatedImagePlaceholder(animationProgress: State<Float>, modifier: Modifier = Modifier) {
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
