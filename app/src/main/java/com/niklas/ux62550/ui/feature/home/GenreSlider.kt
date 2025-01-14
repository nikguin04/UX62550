package com.niklas.ux62550.ui.feature.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
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
    // observe list scrolling
    val reachedBottom: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 && lastVisibleItem?.index == listState.layoutInfo.totalItemsCount - buffer
        }
    }
    // load more if scrolled to bottom
    LaunchedEffect(reachedBottom) {
        if (reachedBottom) {
            //Log.w("bottom_tag", "Triggered bottom") // TEMPORARY! making sure we dont load 40 items in the start, only 20
            discoverViewModel.lastGenreId?.let { discoverViewModel.getDiscover(it, discoverViewModel.lastPage+1) }
                ?: run { throw Exception("Last genre id not set for discoverViewModel, reached bottom before initializing")  }
        }
    }

    Row(
        Modifier.padding(15.dp,  12.dp,  0.dp,  2.dp)
    ) {
        Text(text = headerTitle)
    }
    val w = Dp(155f)
    val h = Dp(155f/16*9)
    when (discoverUiState) {
        DiscoverItemsUIState.Empty -> {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(h)) // Size placeholder
        }
        is DiscoverItemsUIState.Data -> {
            HorizontalLazyRowMovies(
                Modifier.padding(0.dp, 0.dp),
                w, h,
                discoverUiState.mediaObjects,
                onNavigateToMedia,
                listState,
                fetchEnBackdrop = true
            )
        }
        else -> {}
    }


}