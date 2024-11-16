package com.niklas.ux62550.ui.screen_home

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.R
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

    private val mutableMediaItemsState = MutableStateFlow<MediaItemsUIState>(MediaItemsUIState.Empty)
    val mediaItemsState: StateFlow<MediaItemsUIState> = mutableMediaItemsState

    init {
        viewModelScope.launch {
            while (true) {
                delay(5000L)
                mutableMediaItemsState.update {
                    MediaItemsUIState.Data(
                        mediaItems = MediaItems.shuffled()
                    )
                }
            }
        }
    }

    fun initPreview() {
        mutableMediaItemsState.update {
            MediaItemsUIState.Data(
                mediaItems = MediaItems
            )
        }
    }
}

sealed class MediaItemsUIState {
    data object Empty : MediaItemsUIState()
    data class Data(val mediaItems: List<MediaItem>) : MediaItemsUIState()
}
