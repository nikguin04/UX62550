package com.niklas.ux62550.ui.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.data.remote.RemoteMediaDataSource.Companion.BASE_IMAGE_URL
import com.niklas.ux62550.models.MediaItem
import com.niklas.ux62550.ui.dpToPx
import com.niklas.ux62550.ui.feature.common.DiscoverViewModelFactory
import com.niklas.ux62550.ui.feature.common.ImageViewModel
import com.niklas.ux62550.ui.feature.common.ImageViewModelFactory
import com.niklas.ux62550.ui.feature.common.ImagesDataUIState
import kotlin.math.absoluteValue
import kotlin.math.roundToInt


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
            MediaItem(
                uri = items[page].backdrop_path,
                modifier = Modifier
                    .clickable(onClick = { onNavigateToMedia(items[page])})
                    .align(Alignment.CenterHorizontally)
                    .size(Dp(w),Dp(h))
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
                        modifier = modifier
                    )
                } ?: // ELSE
                Box(modifier = modifier) // Red color is to indicate that the media has no english backdrop, this box is TEMPORARY! and for later debugging purposes when making title over media with no english backdrop
                {
                    MediaItem(
                        round = 6.dp,
                        uri = mediaItem.backdrop_path,
                        modifier = Modifier.fillMaxSize()
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
            modifier = modifier
        )
    }


}



@Composable
fun MediaItem(uri: String?, round: Dp = 0.dp, modifier: Modifier = Modifier) {
    val imguri = if (uri!=null) BASE_IMAGE_URL + uri else "https://cataas.com/cat"
    AsyncImage(
        model = imguri,
        contentDescription = null, // TODO: include content description
        modifier = modifier
            .clip(RoundedCornerShape(round))
    )
}