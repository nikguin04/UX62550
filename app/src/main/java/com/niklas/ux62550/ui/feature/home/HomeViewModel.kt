package com.niklas.ux62550.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.domain.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    /*private val mediaItems: List<MediaItem> = listOf(
        MediaItem("Name 1", R.drawable.logo, Color.Blue),
        MediaItem("Name 2", R.drawable.logo, Color.Red),
        MediaItem("Name 3", R.drawable.logo, Color.Green),
    )*/

    private val homeRepository = HomeRepository()

    private val mutableMediaItemsState = MutableStateFlow<MediaItemsUIState>(MediaItemsUIState.Empty)
    val mediaItemsState: StateFlow<MediaItemsUIState> = mutableMediaItemsState

    private val mutableGenreItemsState = MutableStateFlow<GenreItemsUIState>(GenreItemsUIState.Empty)
    val genreItemsState: StateFlow<GenreItemsUIState> = mutableGenreItemsState

    init {
        viewModelScope.launch {
            homeRepository.searchFlow.collect { searchDataObject ->
                mutableMediaItemsState.update { MediaItemsUIState.Data(searchDataObject.results) }
            }
        }
        viewModelScope.launch {
            homeRepository.keywordFlow.collect { searchDataObject ->
                mutableGenreItemsState.update { GenreItemsUIState.Data(searchDataObject.results) }
            }
        }
        getMedia()
        getGenre("213429")
    }
    private fun getMedia() = viewModelScope.launch {
        homeRepository.getMultiSearch("The Office") // TODO: Don't hardcore this, get some proper featured films
    }

    private fun getGenre(keyword_id: String) = viewModelScope.launch {
        homeRepository.getKeywordSearch(keyword_id, 1) // TODO: Don't hardcore this, get some proper genres
    }

    fun initPreview() {
        mutableMediaItemsState.update {
            MediaItemsUIState.Data(
                mediaObjects = listOf() // TODO: Fill this for preview
            )
        }
    }
}

sealed class MediaItemsUIState {
    data object Empty : MediaItemsUIState()
    data class Data(val mediaObjects: List<MediaObject>) : MediaItemsUIState()
}

sealed class GenreItemsUIState {
    data object Empty : GenreItemsUIState()
    data class Data(val mediaObjects: List<MediaObject>) : GenreItemsUIState()
}
