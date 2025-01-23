package com.niklas.ux62550.ui.feature.mediadetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.data.examples.MediaDetailExample
import com.niklas.ux62550.data.examples.SearchDataExamples
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.data.model.MovieDetailObject
import com.niklas.ux62550.data.model.Provider
import com.niklas.ux62550.data.model.Result
import com.niklas.ux62550.data.model.TrailerObject
import com.niklas.ux62550.data.remote.RemoteFirebase
import com.niklas.ux62550.di.DataModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieViewModel() : ViewModel() {
    private val mediaExtendedDetailsRepository = DataModule.mediaExtendedDetailsRepository
    private val mediaDetailsRepository = DataModule.mediaDetailRepository
    private val _movieReviewID = MutableStateFlow<Map<String, Double>>(emptyMap())
    val movieReviewID: StateFlow<Map<String, Double>> = _movieReviewID

    fun getDetailReviews(movieID: Int) {
        viewModelScope.launch {
            val reviews = RemoteFirebase.getReview(movieID)
            _movieReviewID.value = reviews
        }
    }

    private fun getSimilarMovies(MovieID : Int) = viewModelScope.launch {
        mediaExtendedDetailsRepository.getSimilarsMovies(MovieID)
    }

    private fun getProviderForMovies(MovieID: Int) = viewModelScope.launch {
        mediaExtendedDetailsRepository.getProvider(MovieID) // TODO: Don't hardcore this, get some proper featured films
    }

    private fun getTrailerForMovies(MovieID: Int) = viewModelScope.launch {
        mediaExtendedDetailsRepository.getTrailer(MovieID) // TODO: Don't hardcore this, get some proper featured films
    }

    fun updateToDatabase(media: MediaObject, remove: Boolean = false) {
        viewModelScope.launch {
            RemoteFirebase.UpdateToWatchList(media, remove)
        }
    }

    private val mutableMovieState = MutableStateFlow<MovieState>(MovieState.Empty)
    val movieState: StateFlow<MovieState> = mutableMovieState

    private val mutableSimilarMovieState = MutableStateFlow<SimilarMovieState>(SimilarMovieState.Empty)
    val similarMediaState: StateFlow<SimilarMovieState> = mutableSimilarMovieState

    private val mutableProviderState = MutableStateFlow<ProviderState>(ProviderState.Empty)
    val providerState: StateFlow<ProviderState> = mutableProviderState

    private val mutableTrailerState = MutableStateFlow<TrailerState>(TrailerState.Empty)
    val trailerState: StateFlow<TrailerState> = mutableTrailerState

    fun init(media: MediaObject) {
        viewModelScope.launch {
            mediaDetailsRepository.getWithKey(
                itemKey = media.id,
                getUnit = { (mediaDetailsRepository::getMoviesDetails)(media.id) },
                scope = viewModelScope
            ).collect { movieDetailObjectResult ->
                if (movieDetailObjectResult.isSuccess) {
                    mutableMovieState.update { MovieState.Data(movieDetailObjectResult.getOrThrow()) }
                } else {
                    mutableMovieState.update { MovieState.Error }
                }
            }
        }
        viewModelScope.launch {
            mediaExtendedDetailsRepository.similarFlow.collect { searchDataObject ->
                mutableSimilarMovieState.update {
                    run { // Append media_type before updating data
                        if (searchDataObject.isSuccess) {
                            searchDataObject.getOrThrow().results.forEach { res -> res.media_type = "movie" } // TODO: Movies are hardcoded in discover, make this change smoothly when fetching TV
                            SimilarMovieState.Data(searchDataObject.getOrThrow().results)
                        } else { SimilarMovieState.Error }
                    }
                }
            }
        }
        viewModelScope.launch {
            mediaExtendedDetailsRepository.providerFlow.collect { ProviderDataObject ->
                mutableProviderState.update {
                    if (ProviderDataObject.isSuccess) { ProviderState.Data(ProviderDataObject.getOrThrow().result) }
                    else { ProviderState.Error }
                }
            }
        }
        viewModelScope.launch {
            mediaExtendedDetailsRepository.trailerFlow.collect { TrailerObject ->
                mutableTrailerState.update {
                    if (TrailerObject.isSuccess) { TrailerState.Data(TrailerObject.getOrThrow()) }
                    else { TrailerState.Error }
                }
            }
        }

        getSimilarMovies(MovieID = media.id)
        getProviderForMovies(MovieID = media.id)
        getTrailerForMovies(MovieID = media.id)
    }

    fun initPreview() {
        mutableMovieState.update {
            MovieState.Data(
                mediaDetailObject = MediaDetailExample.MediaDetailObjectExample
            )
        }
        mutableSimilarMovieState.update {
            SimilarMovieState.Data(
                similarMoviesObject = listOf(SearchDataExamples.MediaObjectExample, SearchDataExamples.MediaObjectExample, SearchDataExamples.MediaObjectExample)
            )
        }

        mutableProviderState.update {
            ProviderState.Data(
                providerDataObject = mapOf<String, Result>(
                    "DK" to Result(link = "", flatrate = listOf(Provider(logoPath = "/pbpMk2JmcoNnQwx5JGpXngfoWtp.jpg", providerId = 0, providerName = "Netflix")))
                )
            )
        }

        mutableTrailerState.update {
            TrailerState.Data(
                trailerObject = MediaDetailExample.TrailerObjectExample
            )
        }
    }
}

sealed class MovieState {
    data object Empty : MovieState()
    data class Data(val mediaDetailObject: MovieDetailObject) : MovieState()
    data object Error : MovieState()
}

sealed class SimilarMovieState {
    object Empty : SimilarMovieState()
    data class Data(val similarMoviesObject: List<MediaObject>) : SimilarMovieState()
    data object Error : SimilarMovieState()
}

sealed class ProviderState {
    object Empty : ProviderState()
    data class Data(val providerDataObject: Map<String, Result>) : ProviderState()
    data object Error : ProviderState()
}

sealed class TrailerState {
    object Empty : TrailerState()
    data class Data(val trailerObject: TrailerObject) : TrailerState()
    data object Error : TrailerState()
}
