package com.niklas.ux62550.ui.feature.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.ui.feature.common.DiscoverItemsUIState
import com.niklas.ux62550.ui.feature.common.DiscoverViewModel


// TODO: Make this usable with a proper interface maybe
@Composable
fun DiscoverSlider(discoverViewModel: DiscoverViewModel, headerTitle: String, onNavigateToMedia: (MediaObject) -> Unit) {
    val discoverUiState = discoverViewModel.discoverItemsState.collectAsState().value

    when (discoverUiState) {
        DiscoverItemsUIState.Empty -> {

        }
        is DiscoverItemsUIState.Data -> {
            Row(
                Modifier.padding(15.dp,  12.dp,  0.dp,  2.dp)
            ) {
                Text(text = headerTitle)
            }
            HorizontalLazyRowMovies(
                Modifier.padding(0.dp, 0.dp),
                Dp(155f),
                Dp(155f/16*9),
                discoverUiState.mediaObjects,
                onNavigateToMedia,
                fetchEnBackdrop = true
            )
        }
        else -> {}
    }


}