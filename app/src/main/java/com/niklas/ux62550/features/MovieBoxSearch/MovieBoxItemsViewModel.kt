package com.niklas.ux62550.features.MovieBoxSearch

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.R
import com.niklas.ux62550.models.MediaItem
import com.niklas.ux62550.models.MovieBoxItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieBoxItemsViewModel : ViewModel() {

    private val MovieBoxItems: List<MovieBoxItem> = listOf(
        MovieBoxItem("Name 1", R.drawable.logo, Color.Blue, genre="Lol", rating=3f),
        MovieBoxItem("Name 2", R.drawable.logo, Color.Red, genre="Lol", rating=3f),
        MovieBoxItem("Name 3", R.drawable.logo, Color.Green, genre="Lol", rating=3f),
    )

    private val mutableMovieBoxItemsState = MutableStateFlow<MovieBoxItemsUIState>(MovieBoxItemsUIState.Empty)
    val MovieBoxItemsState: StateFlow<MovieBoxItemsUIState> = mutableMovieBoxItemsState

    init {
        viewModelScope.launch {
            while (true) {
                delay(5000L)
                mutableMovieBoxItemsState.update {
                    MovieBoxItemsUIState.Data(
                        movieBoxItems = MovieBoxItems.shuffled()
                    )
                }
            }
        }
    }
}

sealed class MovieBoxItemsUIState {
    data object Empty: MovieBoxItemsUIState()
    data class Data(val movieBoxItems: List<MovieBoxItem>): MovieBoxItemsUIState()
}