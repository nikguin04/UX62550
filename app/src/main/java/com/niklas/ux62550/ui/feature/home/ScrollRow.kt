package com.niklas.ux62550.ui.feature.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.niklas.ux62550.R
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.data.remote.RemoteMediaDataSource.Companion.BASE_IMAGE_URL
import com.niklas.ux62550.ui.feature.common.ImageViewModel
import com.niklas.ux62550.ui.feature.common.ImageViewModelFactory
import com.niklas.ux62550.ui.feature.common.ImagesDataUIState
import com.niklas.ux62550.ui.feature.common.MediaItem
import kotlin.math.absoluteValue


@Composable
fun HomeFeaturedMediaHorizontalPager(items: List<MediaObject>, onNavigateToMedia: (MediaObject) -> Unit) {
    val pagerState = rememberPagerState(pageCount = { items.size }, initialPage = items.size/2)
    val w = 350f
    val h = w/16*9
    val gap = 10f
    HorizontalPager(state = pagerState,
        contentPadding = PaddingValues(start = Dp((LocalConfiguration.current.screenWidthDp - w) / 2)),
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        pageSize = PageSize.Fixed(Dp(w)),
        pageSpacing = Dp(gap)
    )
    { page ->
        Card(
            Modifier
                .size(Dp(w), Dp(h))
                .graphicsLayer {
                    // Calculate the absolute offset for the current page from the
                    // scroll position. We use the absolute value which allows us to mirror
                    // any effects for both directions
                    val pageOffset = (
                        (pagerState.currentPage - page) + pagerState
                            .currentPageOffsetFraction
                        ).absoluteValue

                    // We animate the alpha, between 50% and 100%
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }

        )  {
            // Card content
            MediaItemBackdropIntercept(
                modifier = Modifier
                    .clickable(onClick = { onNavigateToMedia(items[page])})
                    .align(Alignment.CenterHorizontally)
                    .size(Dp(w),Dp(h)),
                mediaItem = items[page],
                fetchEnBackdrop = true
            )
        }
    }
    Box(Modifier.size(4.dp))
    HorizontalDotIndexer(
        Modifier.size(LocalConfiguration.current.screenWidthDp.dp, 12.dp),
        items,
        pagerState
    )

}

@Composable
fun HorizontalDotIndexer(modifier: Modifier, items: List<MediaObject>, pagerState: PagerState) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed{ index, media ->
            Icon(
                Icons.Filled.Circle,
                contentDescription = "Index Point",
                modifier = Modifier
                    .padding(1.5.dp, 0.dp, 1.5.dp, 0.dp)
                    .size(12.dp)
                    .shadow(
                        elevation = 4.dp,
                        shape = CircleShape,
                        ambientColor = Color.Black.copy(alpha = 255f), // Slightly less opaque for a softer effect
                        spotColor = Color.Black.copy(alpha = 255f)
                    ),
                tint = if (index==pagerState.currentPage) LocalContentColor.current else LocalContentColor.current.copy(alpha = 0.5f)

            )
     }
    }
}

@Composable
fun HorizontalLazyRowMovies(
    modifier: Modifier = Modifier,
    width: Dp,
    height: Dp,
    items: List<MediaObject>,
    onNavigateToMedia: (MediaObject) -> Unit,
    fetchEnBackdrop: Boolean = false
) {
    // LazyRow with snapping effect
    LazyRow(
        modifier = modifier.fillMaxWidth()
    ) {
        items.forEachIndexed { index, mediaItem ->
            item {
                MediaItemBackdropIntercept(
                    modifier = Modifier
                        .clickable(onClick = { onNavigateToMedia(mediaItem) })
                        .padding(
                            (if (index == 0) 12f.dp else 6f.dp), 0.dp,
                            (if (index == items.size - 1) 12f.dp else 6f.dp), 0.dp,
                        )
                        .size(width,height),
                    fetchEnBackdrop,mediaItem

                )
            }
        }
    }
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
        when (imagesDataUIState) {
            ImagesDataUIState.Empty -> {
                // TODO: CWL loading page?

                MediaItem(
                    round = 6.dp,
                    uri = mediaItem.backdrop_path,
                    modifier = modifier
                )
            }

            is ImagesDataUIState.Data -> {
                val enBackdrop = imagesDataUIState.media.getFirstEnBackdrop()
                enBackdrop?.let {
                    MediaItem(
                        round = 6.dp,
                        uri = it.file_path,
                        modifier = modifier,
                        size = ImageSize.BACKDROP
                    )
                } ?: // ELSE
                Box(modifier = modifier) // Red color is to indicate that the media has no english backdrop, this box is TEMPORARY! and for later debugging purposes when making title over media with no english backdrop
                {
                    MediaItem(
                        round = 6.dp,
                        uri = mediaItem.backdrop_path, // TODO: catch null case here
                        modifier = Modifier.fillMaxSize(),
                        size = ImageSize.BACKDROP
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




