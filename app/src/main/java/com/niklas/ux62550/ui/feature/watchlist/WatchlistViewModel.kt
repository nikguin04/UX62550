package com.niklas.ux62550.ui.feature.watchlist

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.data.model.MovieDetailObject
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

    fun initPreview() {
        val movies = listOf(
            MediaObject(title="RED: The Movie", release_date="2022", vote_average=3.5f, id=1, popularity=1f, backdrop_path = "/2meX1nMdScFOoV4370rqHWKmXhY.jpg"),
            MediaObject(title="Where's the Blue?", release_date="2014", vote_average=3.5f, id=1, popularity=1f/*, backdrop_path = "/8UOAYjhwSF3aPZhm6wgLuyHRyrR.jpg"*/),
            MediaObject(title="Green Man", release_date="1998", vote_average=3.5f, id=1, popularity=1f, backdrop_path = "/2meX1nMdScFOoV4370rqHWKmXhY.jpg"),
        )
        mutableMovieState.update {
            MovieState.Data(
                mediaDetailObjects = MovieDetailObject()
            )
        }
    }
}

sealed class MovieFromIdFromDB {
    data object Empty : MovieFromIdFromDB()
    data class Data(val movies: SearchDataObject) : MovieFromIdFromDB()
}