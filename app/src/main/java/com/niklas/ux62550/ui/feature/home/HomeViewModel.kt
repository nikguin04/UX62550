package com.niklas.ux62550.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.data.examples.SearchDataExamples
import com.niklas.ux62550.data.model.GenreObject
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

    private val mutableMovieGenresState = MutableStateFlow<GenresDataState>(GenresDataState.Empty)
    val movieGenresState: StateFlow<GenresDataState> = mutableMovieGenresState


    init {
        viewModelScope.launch {
            homeRepository.featuredMediaFlow.collect { searchDataObject ->
                mutableMediaItemsState.update { MediaItemsUIState.Data(searchDataObject.results) }
            }
        }
        viewModelScope.launch {
            homeRepository.genreFetchFlow.collect { genreDataObject ->
                mutableMovieGenresState.update { GenresDataState.Data(genreDataObject.genres) }
            }
        }
        getFeaturedMedia()
        getMovieGenres()
    }
    private fun getFeaturedMedia() = viewModelScope.launch {
        homeRepository.getTrending( "day")
    }
    private fun getMovieGenres() = viewModelScope.launch {
        homeRepository.getGenres()
    }


    fun initPreview() {
        mutableMediaItemsState.update {
            MediaItemsUIState.Data(
                mediaObjects = listOf(
                    SearchDataExamples.MediaObjectExample,
                    SearchDataExamples.MediaObjectExample,
                    SearchDataExamples.MediaObjectExample
                ) // TODO: Fill this for preview
            )
        }
        mutableMovieGenresState.update {
            GenresDataState.Data(
                genres = listOf(
                    GenreObject(28, "Action"),
                    GenreObject(12, "Adventure"),
                    GenreObject(16, "Animation"),
                    GenreObject(35, "Comedy"),
                    GenreObject(80, "Crime"),
                    GenreObject(99, "Documentary"),
                    GenreObject(18, "Drama")
                )
            )
        }
    }
}

sealed class MediaItemsUIState {
    data object Empty : MediaItemsUIState()
    data class Data(
        val mediaObjects: List<MediaObject>
    ) : MediaItemsUIState()
}

sealed class GenresDataState {
    data object Empty : GenresDataState()
    data class Data(
        val genres: List<GenreObject>
    ) : GenresDataState()
}

