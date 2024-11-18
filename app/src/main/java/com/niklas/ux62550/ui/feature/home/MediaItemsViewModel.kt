package com.niklas.ux62550.ui.feature.home

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.R
import com.niklas.ux62550.models.MediaItem
import com.niklas.ux62550.models.Movie
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes

class MediaItemsViewModel : ViewModel() {
    private val mediaItems: List<MediaItem> = listOf(
        MediaItem("Name 1", R.drawable.logo, Color.Blue),
        MediaItem("Name 2", R.drawable.logo, Color.Red),
        MediaItem("Name 3", R.drawable.logo, Color.Green),
    )
    private val movies = listOf(
        Movie("RED: The Movie", "2022", 131.minutes, 3.5, "N/A", listOf("Action", "Dinosaur Adventure", "Romance"), 18, Color.Red),
        Movie("Where's the Blue?", "2014", 96.minutes, 2.0, "N/A", listOf("Documentary", "Comedy"), 13, Color.Blue),
        Movie("Green Man", "1998", 113.minutes, 3.0, "N/A", listOf("Thriller", "Horror"), 15, Color.Green),
    )

    private val mutableMediaItemsState = MutableStateFlow<MediaItemsUIState>(MediaItemsUIState.Empty)
    val mediaItemsState: StateFlow<MediaItemsUIState> = mutableMediaItemsState

    init {
        viewModelScope.launch {
            while (true) {
                delay(5000L)
                mutableMediaItemsState.update {
                    MediaItemsUIState.Data(
                        mediaItems = mediaItems.shuffled()
                    )
                }
            }
        }
    }

    fun initPreview() {
        mutableMediaItemsState.update {
            MediaItemsUIState.Data(
                mediaItems = mediaItems
            )
        }
    }
}

sealed class MediaItemsUIState {
    data object Empty : MediaItemsUIState()
    data class Data(val mediaItems: List<MediaItem>) : MediaItemsUIState()
}
