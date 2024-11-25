package com.niklas.ux62550.ui.feature.mediadetails

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.R
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.data.model.MovieDetailObject
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

    private val movie = Movie(
        name = "RED: The Movie",
        year = "2022",
        duration = 131.minutes,
        rating = 3.5,
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat",
        genres = listOf("Action", "Dinosaur Adventure", "Romance"),
        pgRating = 18,
        tempColor = Color.Red
    )

    private val similarMedia = listOf(
        MediaItem("Name 1", R.drawable.logo, Color.Blue, "", ""),
        MediaItem("Name 2", R.drawable.logo, Color.Red, "", ""),
        MediaItem("Name 3", R.drawable.logo, Color.Green, "", ""),
    )

    private val mutableMovieState = MutableStateFlow<MovieState>(MovieState.Empty)
    val movieState: StateFlow<MovieState> = mutableMovieState

    private val mutableSimilarMediaState = MutableStateFlow<List<MediaItem>>(similarMedia)
    val similarMediaState: StateFlow<List<MediaItem>> = mutableSimilarMediaState

    init {
        viewModelScope.launch {
            mutableMovieState.update {
                mediaDetailsRepository.detailFlow
                    .collect { movieDetailObject ->
                        mutableMovieState.update {
                            MovieState.Data(movieDetailObject)
                        }
                    }
            }
        }
        getDetails()
    }
}

sealed class MovieState {
    data object Empty : MovieState()
    data class Data(val mediaDetailObjects: MovieDetailObject) : MovieState()
}
