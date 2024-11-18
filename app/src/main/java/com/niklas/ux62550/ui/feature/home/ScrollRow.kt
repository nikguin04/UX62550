package com.niklas.ux62550.ui.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
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
import com.niklas.ux62550.models.MediaItem
import com.niklas.ux62550.models.figmaPxToDp_h
import com.niklas.ux62550.models.figmaPxToDp_w
import com.niklas.ux62550.ui.dpToPx
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Composable
fun HorizontalLazyRowWithSnapEffect(items: List<MediaItem>, onNavigateToMedia: (String) -> Unit) {
    val itemWidth = figmaPxToDp_w(300f) + figmaPxToDp_w(10f) * 2 // width + padding*"
    val halfScreenWidth = LocalConfiguration.current.screenWidthDp.dp / 2
    val halfItemWidth = itemWidth / 2
    val offsetToCenter = halfScreenWidth - halfItemWidth
    val pixelOffset: Int = offsetToCenter.dpToPx().roundToInt()
    val listState = rememberLazyListState(1, -pixelOffset) // To manage the scroll state

    // LazyRow with snapping effect
    LazyRow(
        state = listState,
        flingBehavior = rememberSnapFlingBehavior(listState), // Snap to item effect
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 0.dp, 0.dp, 0.dp)
    ) {
        items.forEachIndexed { index, mediaItem ->
            item {
                PromoItem(
                    width = figmaPxToDp_w(300f),
                    height = figmaPxToDp_h(200f),
                    round = figmaPxToDp_w(20f),
                    color = mediaItem.tempColor,
                    modifier = Modifier
                        .padding(figmaPxToDp_w(10f))
                        .clickable(onClick = { onNavigateToMedia(mediaItem.name) })
                )
            }
        }
    }
}

@Composable
fun HomeFeaturedMediaHorizontalPager(items: List<MediaItem>, onNavigateToMedia: (String) -> Unit) {
    val pagerState = rememberPagerState(pageCount = { items.size }, initialPage = items.size/2)
    val w = 350f
    val h = w/16*9
    HorizontalPager(state = pagerState,
        contentPadding = PaddingValues(start = Dp((LocalConfiguration.current.screenWidthDp - w) / 2)),
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically)
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
            PromoItem(
                width = Dp(w),
                height = Dp(h),
                round = 0.dp,
                color = items[page].tempColor,
                modifier = Modifier
                    .clickable(onClick = { onNavigateToMedia(items[page].name) })
                    .align(Alignment.CenterHorizontally)
            )
        }
    }

}

@Composable
fun HorizontalLazyRowMovies(
    modifier: Modifier = Modifier,
    widthFigmaPx: Float,
    heightFigmaPx: Float,
    items: List<MediaItem>,
    onNavigateToMedia: (String) -> Unit
) {
    // LazyRow with snapping effect
    LazyRow(
        modifier = modifier.fillMaxWidth()
    ) {
        items.forEachIndexed { index, mediaItem ->
            item {
                PromoItem(
                    width = figmaPxToDp_w(widthFigmaPx),
                    height = figmaPxToDp_h(heightFigmaPx),
                    round = figmaPxToDp_w(5f),
                    color = mediaItem.tempColor,
                    modifier = Modifier
                        .clickable(onClick = { onNavigateToMedia(mediaItem.name) })
                        .padding(
                            figmaPxToDp_w(if (index == 0) 10f else 5f), 0.dp,
                            figmaPxToDp_w(if (index == items.size - 1) 10f else 5f), 0.dp
                        )
                )
            }
        }
    }
}

@Composable
fun PromoItem(width: Dp, height: Dp, round: Dp, color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(round))
            .background(color)
            .size(width, height)
            .padding()
    )
}
