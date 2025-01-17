package com.niklas.ux62550.ui.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.ui.feature.common.DiscoverItemsUIState
import com.niklas.ux62550.ui.feature.common.DiscoverViewModel

private const val buffer = 2

// TODO: Make this usable with a proper interface maybe
@Composable
fun DiscoverSlider(discoverViewModel: DiscoverViewModel, headerTitle: String, onNavigateToMedia: (MediaObject) -> Unit) {
    val discoverUiState = discoverViewModel.discoverItemsState.collectAsState().value

    val listState = rememberLazyListState()
    // Observe list scrolling
    val reachedBottom: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 && lastVisibleItem?.index == listState.layoutInfo.totalItemsCount - buffer
        }
    }
    // Load more if scrolled to bottom
    LaunchedEffect(reachedBottom) {
        if (reachedBottom) {
            discoverViewModel.getDiscover(discoverViewModel.lastPage + 1)
        }
    }

    Row(Modifier.padding(15.dp, 12.dp, 0.dp, 2.dp)) {
        Text(text = headerTitle)
    }
    val w = 155.dp
    val h = 155.dp / 16 * 9
    when (discoverUiState) {
        DiscoverItemsUIState.Empty -> {
            // Size placeholder
            Box(Modifier.fillMaxWidth().height(h))
        }
        is DiscoverItemsUIState.Data -> {
            HorizontalLazyRowMovies(
                width = w,
                height = h,
                items = discoverUiState.mediaObjects,
                onNavigateToMedia = onNavigateToMedia,
                rowListState = listState,
                fetchEnBackdrop = true
            )
        }
    }
}
