package com.niklas.ux62550.ui.screen_search

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.R
import com.niklas.ux62550.models.MovieBox
import com.niklas.ux62550.models.NonMovieBox
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieBoxItemsViewModel : ViewModel() {

    private val movieBoxes: List<MovieBox> = listOf(
        MovieBox("Name 1", R.drawable.logo, Color.Blue, genre="Lol", rating=3f),
        MovieBox("Name 2", R.drawable.logo, Color.Red, genre="Lol", rating=3f),
        MovieBox("Name 3", R.drawable.logo, Color.Green, genre="Lol", rating=3f),
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
                        movieBoxes = movieBoxes.shuffled()
                    )
                }
            }
        }
    }
}

sealed class MovieBoxItemsUIState {
    data object Empty: MovieBoxItemsUIState()
    data class Data(val movieBoxes: List<MovieBox>): MovieBoxItemsUIState()
}

sealed class NonMovieBoxItemsUIState {
    data object Empty: NonMovieBoxItemsUIState()
    data class Data(val nonMovieBoxes: List<NonMovieBox>): NonMovieBoxItemsUIState()
}

