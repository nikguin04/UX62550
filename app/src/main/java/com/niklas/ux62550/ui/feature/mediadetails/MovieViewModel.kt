package com.niklas.ux62550.ui.feature.mediadetails

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.R
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.data.model.MovieDetailObject
import com.niklas.ux62550.data.model.SimilarMoviesObject
import com.niklas.ux62550.data.model.SimilarMoviesPic
import com.niklas.ux62550.domain.HomeRepository
import com.niklas.ux62550.domain.MediaDetailsRepository
import com.niklas.ux62550.models.MediaItem
import com.niklas.ux62550.models.Movie
import com.niklas.ux62550.ui.feature.home.MediaItemsUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes

class MovieViewModel : ViewModel() {
    private val mediaDetailsRepository = MediaDetailsRepository()

    private fun getDetails() = viewModelScope.launch {
        mediaDetailsRepository.getMoviesDetails(205321) // TODO: Don't hardcore this, get some proper featured films
    }
    private fun getSimilarMovies() = viewModelScope.launch {
        mediaDetailsRepository.getSimilarsMovies(205321) // TODO: Don't hardcore this, get some proper featured films
    }

    private val similarMedia = listOf(
        MediaItem("Name 1", R.drawable.logo, Color.Blue, "", ""),
        MediaItem("Name 2", R.drawable.logo, Color.Red, "", ""),
        MediaItem("Name 3", R.drawable.logo, Color.Green, "", ""),
    )

    private val mutableMovieState = MutableStateFlow<MovieState>(MovieState.Empty)
    val movieState: StateFlow<MovieState> = mutableMovieState

    private val mutableSimilarMovieState = MutableStateFlow<SimilarMovieState>(SimilarMovieState.Empty)
    val similarMediaState: StateFlow<SimilarMovieState> = mutableSimilarMovieState

    init {
        viewModelScope.launch {
            mediaDetailsRepository.detailFlow.collect { movieDetailObject ->
                mutableMovieState.update {
                    MovieState.Data(movieDetailObject)
                }
            }
        }

        viewModelScope.launch {
            mediaDetailsRepository.similarFlow.collect { similarMoviesObject ->
                mutableSimilarMovieState.update {
                    SimilarMovieState.Data(similarMoviesObject.resultsSimilarMovies)
                }
            }
        }
        getDetails()
        getSimilarMovies()
    }
}

sealed class MovieState {
    data object Empty : MovieState()
    data class Data(val mediaDetailObjects: MovieDetailObject) : MovieState()
}
sealed class SimilarMovieState {
    object Empty : SimilarMovieState()
    data class Data(val similarMoviesObject: List<SimilarMoviesPic>) : SimilarMovieState()
}

