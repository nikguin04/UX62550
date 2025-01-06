package com.niklas.ux62550.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.data.examples.SearchDataExamples
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.domain.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName

class HomeViewModel : ViewModel() {
    /*private val mediaItems: List<MediaItem> = listOf(
        MediaItem("Name 1", R.drawable.logo, Color.Blue),
        MediaItem("Name 2", R.drawable.logo, Color.Red),
        MediaItem("Name 3", R.drawable.logo, Color.Green),
    )*/

    private val homeRepository = HomeRepository()

    private val mutableMediaItemsState = MutableStateFlow<MediaItemsUIState>(MediaItemsUIState.Empty)
    val mediaItemsState: StateFlow<MediaItemsUIState> = mutableMediaItemsState


    init {
        viewModelScope.launch {
            homeRepository.searchFlow.collect { searchDataObject ->
                mutableMediaItemsState.update { MediaItemsUIState.Data(searchDataObject.results) }
            }
        }
        getMedia()
    }
    private fun getMedia() = viewModelScope.launch {
        homeRepository.getSearch("movie","pixar cars") // TODO: Don't hardcore this, get some proper featured films
    }


    fun initPreview() {
        mutableMediaItemsState.update {
            MediaItemsUIState.Data(
                mediaObjects = listOf(
                    SearchDataExamples.MediaObjectExample
                ) // TODO: Fill this for preview
            )
        }
    }
}

sealed class MediaItemsUIState {
    data object Empty : MediaItemsUIState()
    data class Data(val mediaObjects: List<MediaObject>) : MediaItemsUIState()
}

