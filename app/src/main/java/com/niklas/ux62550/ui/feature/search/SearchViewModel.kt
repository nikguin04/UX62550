package com.niklas.ux62550.ui.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.data.model.SearchDataObject
import com.niklas.ux62550.di.DataModule
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchViewModel : ViewModel() {
    private val searchRepository = DataModule.searchRepository

    private val mutableMovieItemsUIState = MutableStateFlow<MovieItemsUIState>(MovieItemsUIState.Empty)
    val movieItemsUIState: StateFlow<MovieItemsUIState> = mutableMovieItemsUIState

    private val searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            searchQuery.debounce(200)
                .collect { query ->
                    if (query.isNotBlank()) {
                        getDetails(query)
                    }
                }
        }

        viewModelScope.launch {
            searchRepository.searchFlow.collect { searchDataObject ->
                mutableMovieItemsUIState.update {
                    if (searchDataObject.isSuccess) { MovieItemsUIState.Data(searchDataObject.getOrThrow()) }
                    else { MovieItemsUIState.Error }
                }
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }

    private fun getDetails(query: String) = viewModelScope.launch {
        searchRepository.getUserSearch(query) // TODO: Don't hardcore this, get some proper featured films
    }

    fun initPreview() {
        val movies = listOf(
            MediaObject(title = "RED: The Movie", releaseDate = "2022", voteAverage = 3.5f, id = 1, popularity = 1f, backdropPath = "/2meX1nMdScFOoV4370rqHWKmXhY.jpg"),
            MediaObject(title = "Where's the Blue?", releaseDate = "2014", voteAverage = 3.5f, id = 1, popularity = 1f/*, backdropPath = "/8UOAYjhwSF3aPZhm6wgLuyHRyrR.jpg"*/),
            MediaObject(title = "Green Man", releaseDate = "1998", voteAverage = 3.5f, id = 1, popularity = 1f, backdropPath = "/2meX1nMdScFOoV4370rqHWKmXhY.jpg"),
        )
        mutableMovieItemsUIState.update {
            MovieItemsUIState.Data(
                movies = SearchDataObject(totalResults = 1, page = 1, totalPages = 1, results = movies)
            )
        }
    }
}

sealed class MovieItemsUIState {
    data object Empty : MovieItemsUIState()
    data object Error : MovieItemsUIState()
    data class Data(val movies: SearchDataObject) : MovieItemsUIState()
}
