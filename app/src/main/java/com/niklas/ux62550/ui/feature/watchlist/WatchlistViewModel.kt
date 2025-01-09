package com.niklas.ux62550.ui.feature.watchlist

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.data.remote.RemoteFirebase
import com.niklas.ux62550.data.remote.getWatchList
import com.niklas.ux62550.domain.MediaDetailsRepository
import com.niklas.ux62550.data.model.SearchDataObject
import com.niklas.ux62550.models.Movie
import com.niklas.ux62550.ui.feature.mediadetails.MovieState
import com.niklas.ux62550.ui.feature.search.MovieItemsUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes

class WatchlistViewModel(media: MediaObject) : ViewModel() {


    // firebase
    private val movies = getWatchList()



    // api
    private val mediaDetailsRepository = MediaDetailsRepository()

    private fun getDetails(MovieID : Int) = viewModelScope.launch {
        mediaDetailsRepository.getMoviesDetails(MovieID) // TODO: Don't hardcore this, get some proper featured films
    }


    private val mutableMovieState = MutableStateFlow<MovieState>(MovieState.Empty)
    val movieState: StateFlow<MovieState> = mutableMovieState


    init {
        viewModelScope.launch {
            mediaDetailsRepository.detailFlow.collect { movieDetailObject ->
                mutableMovieState.update {
                    MovieState.Data(movieDetailObject)
                }
            }
        }
        getDetails(MovieID = media.id)
    }

    // having the data from the firebase seach with the api to get the movies
    private val mutableMoviesState = MutableStateFlow<MovieItemsUIState>(MovieItemsUIState.Data(movies.))
    val moviesState: StateFlow<MovieItemsUIState> = mutableMoviesState
}
