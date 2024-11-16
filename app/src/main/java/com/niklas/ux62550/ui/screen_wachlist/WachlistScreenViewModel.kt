package com.niklas.ux62550.ui.screen_wachlist

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.models.Movie
import com.niklas.ux62550.models.NonMovieBox
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes

class MovieBoxItemsViewModel : ViewModel() {

    private val movieBoxes: List<Movie> = listOf(
        Movie("Name 1", "2000", 90.minutes, 3.0, "N/A", listOf(), 13, Color.Blue),
        Movie("Name 2", "2000", 90.minutes, 3.0, "N/A", listOf(), 13, Color.Red),
        Movie("Name 3", "2000", 90.minutes, 3.0, "N/A", listOf(), 13, Color.Green),
    )

    private val mutableMovieBoxItemsState = MutableStateFlow<MovieBoxItemsUIState>(
        MovieBoxItemsUIState.Empty
    )
    val MovieBoxItemsState: StateFlow<MovieBoxItemsUIState> = mutableMovieBoxItemsState

    init {
        viewModelScope.launch {
            while (true) {
                delay(5000L)
                mutableMovieBoxItemsState.update {
                    MovieBoxItemsUIState.Data(
                        movies = movieBoxes.shuffled()
                    )
                }
            }
        }
    }
}

sealed class MovieBoxItemsUIState {
    data object Empty: MovieBoxItemsUIState()
    data class Data(val movies: List<Movie>): MovieBoxItemsUIState()
}



