package com.niklas.ux62550.ui.feature.search

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.data.model.SearchDataObject
import com.niklas.ux62550.domain.MediaDetailsRepository
import com.niklas.ux62550.domain.SearchRepository
import com.niklas.ux62550.models.Movie
import com.niklas.ux62550.models.NonMovieBox
import com.niklas.ux62550.ui.feature.mediadetails.MovieState
import com.niklas.ux62550.ui.feature.mediadetails.ProviderState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes

class SearchViewModel : ViewModel() {
    private val nonMovies = listOf(
        NonMovieBox("Western", Color.Yellow, "Genre"),
        NonMovieBox("John Doe", Color.Green, "Actor"),
    )

    //private val movies = listOf(
    //    Movie("RED: The Movie", "2022", 131.minutes, 3.5, "N/A", listOf("Action", "Dinosaur Adventure", "Romance"), 18, Color.Red),
    //    Movie("Where's the Blue?", "2014", 96.minutes, 2.0, "N/A", listOf("Documentary", "Comedy"), 13, Color.Blue),
    //    Movie("Green Man", "1998", 113.minutes, 3.0, "N/A", listOf("Thriller", "Horror"), 15, Color.Green),
    //)

    private val searchRepository = SearchRepository()


    private fun getDetails() = viewModelScope.launch {
        searchRepository.getUserSearch("bob") // TODO: Don't hardcore this, get some proper featured films
    }


    private val mutableNonMoviesState = MutableStateFlow<NonMovieBoxItemsUIState>(NonMovieBoxItemsUIState.Data(nonMovies))
    val nonMoviesState: StateFlow<NonMovieBoxItemsUIState> = mutableNonMoviesState



    //private val mutableMoviesState = MutableStateFlow<MovieItemsUIState>(MovieItemsUIState.Data(movies))
    //val moviesState: StateFlow<MovieItemsUIState> = mutableMoviesState



    private val mutableMovieItemsUIState = MutableStateFlow<MovieItemsUIState>(MovieItemsUIState.Empty)
    val movieItemsUIState: StateFlow<MovieItemsUIState> = mutableMovieItemsUIState



    init {
        viewModelScope.launch {
            searchRepository.SearchFlow.collect { SearchDataObject ->
                mutableMovieItemsUIState.update {
                    MovieItemsUIState.Data(SearchDataObject)
                }
            }
        }
        getDetails()
    }




}

sealed class MovieItemsUIState {
    data object Empty : MovieItemsUIState()
    data class Data(val movies: SearchDataObject) : MovieItemsUIState()
}

sealed class NonMovieBoxItemsUIState {
    data object Empty : NonMovieBoxItemsUIState()
    data class Data(val nonMovieBoxes: List<NonMovieBox>) : NonMovieBoxItemsUIState()
}
