package com.niklas.ux62550.features.MoiveBoxSearch

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.R
import com.niklas.ux62550.features.MediaItemList.MediaItemsUIState
import com.niklas.ux62550.models.MediaItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MediaItemsViewModel : ViewModel() {

    private val MediaItems: List<MediaItem> = listOf(
        MediaItem("Name 1", R.drawable.logo, Color.Blue),
        MediaItem("Name 2", R.drawable.logo, Color.Red),
        MediaItem("Name 3", R.drawable.logo, Color.Green),
    )

    private val mutableMovieBoxItemsState = MutableStateFlow<MediaItemsUIState>(MediaItemsUIState.Empty)
    val mediaItemsState: StateFlow<MediaItemsUIState> = mutableMovieBoxItemsState

    init {
        viewModelScope.launch {
            while (true) {
                delay(5000L)
                mutableMovieBoxItemsState.update {
                    MediaItemsUIState.Data(
                        mediaItems = MediaItems.shuffled()
                    )
                }
            }
        }
    }
}

sealed class MoiveBoxItemsUIState {
    data object Empty: MoiveBoxItemsUIState()
    data class Data(val mediaItems: List<MediaItem>): MoiveBoxItemsUIState()
}