package com.niklas.ux62550.ui.feature.search

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.data.model.SearchDataObject
import com.niklas.ux62550.domain.MediaDetailsRepository
import com.niklas.ux62550.domain.SearchRepository
import com.niklas.ux62550.models.Movie
import com.niklas.ux62550.models.NonMovieBox
import com.niklas.ux62550.ui.feature.home.MediaItemsUIState
import com.niklas.ux62550.ui.feature.mediadetails.MovieState
import com.niklas.ux62550.ui.feature.mediadetails.ProviderState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes

class SearchViewModel : ViewModel() {
    private val nonMovies = listOf(
        NonMovieBox("Western", Color.Yellow, "Genre"),
        NonMovieBox("John Doe", Color.Green, "Actor"),
    )

    private val searchRepository = SearchRepository()

    private val mutableNonMoviesState = MutableStateFlow<NonMovieBoxItemsUIState>(NonMovieBoxItemsUIState.Data(nonMovies))
    val nonMoviesState: StateFlow<NonMovieBoxItemsUIState> = mutableNonMoviesState

    private val mutableMovieItemsUIState = MutableStateFlow<MovieItemsUIState>(MovieItemsUIState.Empty)
    val movieItemsUIState: StateFlow<MovieItemsUIState> = mutableMovieItemsUIState

    private val _searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            _searchQuery.debounce(200)
                .collect { query ->
                    if (query.isNotBlank()) {
                        getDetails(query)
                    }
                }
        }

        viewModelScope.launch {
            searchRepository.SearchFlow.collect { SearchDataObject ->
                mutableMovieItemsUIState.update {
                    MovieItemsUIState.Data(SearchDataObject)
                }
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    private fun getDetails(query: String) = viewModelScope.launch {
        searchRepository.getUserSearch(query) // TODO: Don't hardcore this, get some proper featured films
    }

    fun initPreview() {
         val movies = listOf(
             MediaObject(title="RED: The Movie", release_date="2022", vote_average=3.5f, id=1, popularity=1f, backdrop_path = "/2meX1nMdScFOoV4370rqHWKmXhY.jpg"),
             MediaObject(title="Where's the Blue?", release_date="2014", vote_average=3.5f, id=1, popularity=1f/*, backdrop_path = "/8UOAYjhwSF3aPZhm6wgLuyHRyrR.jpg"*/),
             MediaObject(title="Green Man", release_date="1998", vote_average=3.5f, id=1, popularity=1f, backdrop_path = "/2meX1nMdScFOoV4370rqHWKmXhY.jpg"),
        )
        mutableMovieItemsUIState.update {
            MovieItemsUIState.Data(
                movies = SearchDataObject(total_results = 1, page = 1, total_pages = 1, results = movies)
            )
        }
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