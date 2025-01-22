package com.niklas.ux62550.ui.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.ui.feature.common.MediaItemBackdropIntercept
import kotlin.math.absoluteValue

@Composable
fun HomeFeaturedMediaHorizontalPager(items: List<MediaObject>, onNavigateToMedia: (MediaObject) -> Unit) {
    val pagerState = rememberPagerState(pageCount = { items.size }, initialPage = items.size / 2)
    val gap = 12.dp
    val peek = 20.dp
    val pageWidth = LocalConfiguration.current.screenWidthDp.dp - (gap + peek) * 2
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = gap + peek),
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        pageSize = PageSize.Fixed(pageWidth),
        pageSpacing = gap
    ) { page ->
        MediaItemBackdropIntercept(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(12.dp))
                .clickable { onNavigateToMedia(items[page]) }
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
                },
            mediaItem = items[page],
            fetchEnBackdrop = true
        )
    }
    Spacer(Modifier.height(4.dp))
    HorizontalDotIndexer(
        modifier = Modifier.fillMaxWidth(),
        items = items,
        pagerState = pagerState
    )
}

@Composable
fun HorizontalDotIndexer(modifier: Modifier, items: List<MediaObject>, pagerState: PagerState) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(3.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, media ->
            Icon(
                Icons.Filled.Circle,
                contentDescription = "Index Point",
                modifier = Modifier
                    .size(12.dp)
                    .shadow(
                        elevation = 4.dp,
                        shape = CircleShape
                    ),
                tint = if (index == pagerState.currentPage) LocalContentColor.current else LocalContentColor.current.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun HorizontalLazyRowMovies(
    modifier: Modifier = Modifier,
    width: Dp,
    height: Dp,
    edgeGap: Dp,
    betweenGap: Dp,
    items: List<MediaObject>,
    onNavigateToMedia: (MediaObject) -> Unit,
    rowListState: LazyListState? = null,
    fetchEnBackdrop: Boolean = false
) {
    // LazyRow with snapping effect
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        state = rowListState ?: rememberLazyListState(),
        horizontalArrangement = Arrangement.spacedBy(betweenGap),
        contentPadding = PaddingValues(edgeGap, 0.dp)
    ) {
        items.forEachIndexed { index, mediaItem ->
            item {
                MediaItemBackdropIntercept(
                    modifier = Modifier
                        .size(width, height)
                        .clip(RoundedCornerShape(6.dp))
                        .clickable { onNavigateToMedia(mediaItem) },
                    fetchEnBackdrop, mediaItem
                )
            }
        }
    }
}
