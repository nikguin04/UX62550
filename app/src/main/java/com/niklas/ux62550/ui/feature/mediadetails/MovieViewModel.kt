package com.niklas.ux62550.ui.feature.mediadetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.data.model.MovieDetailObject
import com.niklas.ux62550.data.model.Result
import com.niklas.ux62550.data.model.TrailerObject
import com.niklas.ux62550.domain.MediaDetailsRepository
import com.niklas.ux62550.ui.feature.common.DiscoverItemsUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieViewModel(media: MediaObject) : ViewModel() {
    private val mediaDetailsRepository = MediaDetailsRepository()

    private fun getDetails(MovieID : Int) = viewModelScope.launch {
        mediaDetailsRepository.getMoviesDetails(MovieID) // TODO: Don't hardcore this, get some proper featured films
    }
    private fun getSimilarMovies(MovieID : Int) = viewModelScope.launch {
        mediaDetailsRepository.getSimilarsMovies(MovieID)
    }
    private fun getProviderForMovies(MovieID : Int) = viewModelScope.launch {
        mediaDetailsRepository.getProvider(MovieID) // TODO: Don't hardcore this, get some proper featured films
    }
    private fun getTrailerForMovies(MovieID : Int) = viewModelScope.launch {
        mediaDetailsRepository.getTrailer(MovieID) // TODO: Don't hardcore this, get some proper featured films
    }

    private val mutableMovieState = MutableStateFlow<MovieState>(MovieState.Empty)
    val movieState: StateFlow<MovieState> = mutableMovieState

    private val mutableSimilarMovieState = MutableStateFlow<SimilarMovieState>(SimilarMovieState.Empty)
    val similarMediaState: StateFlow<SimilarMovieState> = mutableSimilarMovieState

    private val mutableProviderState = MutableStateFlow<ProviderState>(ProviderState.Empty)
    val providerState: StateFlow<ProviderState> = mutableProviderState

    private val mutableTrailerState = MutableStateFlow<TrailerState>(TrailerState.Empty)
    val trailerState: StateFlow<TrailerState> = mutableTrailerState


    init {
        viewModelScope.launch {
            mediaDetailsRepository.detailFlow.collect { movieDetailObject ->
                mutableMovieState.update {
                    MovieState.Data(movieDetailObject)
                }
            }
        }
        viewModelScope.launch {
            mediaDetailsRepository.similarFlow.collect { searchDataObject ->
                mutableSimilarMovieState.update {
                    run { // Append media_type before updating data
                        searchDataObject.results.forEach { res -> res.media_type = "movie" } // TODO: Movies are hardcoded in discover, make this change smoothly when fetching TV
                        SimilarMovieState.Data(searchDataObject.results)
                    }

                }
            }
        }
        viewModelScope.launch {
            mediaDetailsRepository.providerFlow.collect { ProviderDataObject ->
                mutableProviderState.update {
                    ProviderState.Data(ProviderDataObject.result)
                }
            }
        }
        viewModelScope.launch {
            mediaDetailsRepository.trailerFlow.collect { TrailerObject ->
                mutableTrailerState.update {
                    TrailerState.Data(TrailerObject)
                }
            }
        }

        getDetails(MovieID = media.id)
        getSimilarMovies(MovieID = media.id)
        getProviderForMovies(MovieID = media.id)
        getTrailerForMovies(MovieID = media.id)
    }
}

class MovieViewModelFactory(private val media: MediaObject) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T { return MovieViewModel(media) as T }
}

sealed class MovieState {
    data object Empty : MovieState()
    data class Data(val mediaDetailObjects: MovieDetailObject) : MovieState()
}
sealed class SimilarMovieState {
    object Empty : SimilarMovieState()
    data class Data(val similarMoviesObject: List<MediaObject>) : SimilarMovieState()
}

sealed class ProviderState {
    object Empty : ProviderState()
    data class Data(val providerDataObject: Map<String,Result>) : ProviderState()
}
sealed class TrailerState {
    object Empty : TrailerState()
    data class Data(val trailerObject: TrailerObject) : TrailerState()
}