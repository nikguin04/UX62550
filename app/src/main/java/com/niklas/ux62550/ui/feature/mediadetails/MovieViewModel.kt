package com.niklas.ux62550.ui.feature.mediadetails

import androidx.lifecycle.ViewModel
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

class MovieViewModel : ViewModel() {
    private val mediaExtendedDetailsRepository = DataModule.mediaExtendedDetailsRepository
    private val mediaDetailsRepository = DataModule.mediaDetailRepository
    private val mutableMovieReviewId = MutableStateFlow<Map<String, Double>>(emptyMap())
    val movieReviewId: StateFlow<Map<String, Double>> = mutableMovieReviewId

    fun getDetailReviews(movieId: Int) {
        viewModelScope.launch {
            val reviews = RemoteFirebase.getReview(movieId)
            mutableMovieReviewId.value = reviews
        }
    }

    private fun getSimilarMovies(movieId: Int) = viewModelScope.launch {
        mediaExtendedDetailsRepository.getSimilarMovies(movieId)
    }

    private fun getProviderForMovies(movieId: Int) = viewModelScope.launch {
        mediaExtendedDetailsRepository.getProvider(movieId) // TODO: Don't hardcore this, get some proper featured films
    }

    private fun getTrailerForMovies(movieId: Int) = viewModelScope.launch {
        mediaExtendedDetailsRepository.getTrailer(movieId) // TODO: Don't hardcore this, get some proper featured films
    }

    fun updateToDatabase(media: MediaObject, remove: Boolean = false) {
        viewModelScope.launch {
            RemoteFirebase.updateToWatchList(media, remove)
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
            mediaExtendedDetailsRepository.similarMoviesFlow.collect { searchDataObject ->
                mutableSimilarMovieState.update {
                    run { // Append media_type before updating data
                        if (searchDataObject.isSuccess) {
                            searchDataObject.getOrThrow().results.forEach { res -> res.mediaType = "movie" } // TODO: Movies are hardcoded in discover, make this change smoothly when fetching TV
                            SimilarMovieState.Data(searchDataObject.getOrThrow().results)
                        } else { SimilarMovieState.Error }
                    }
                }
            }
        }
        viewModelScope.launch {
            mediaExtendedDetailsRepository.providerFlow.collect { providerDataObject ->
                mutableProviderState.update {
                    if (providerDataObject.isSuccess) { ProviderState.Data(providerDataObject.getOrThrow().results) }
                    else { ProviderState.Error }
                }
            }
        }
        viewModelScope.launch {
            mediaExtendedDetailsRepository.trailerFlow.collect { trailerObject ->
                mutableTrailerState.update {
                    if (trailerObject.isSuccess) { TrailerState.Data(trailerObject.getOrThrow()) }
                    else { TrailerState.Error }
                }
            }
        }

        getSimilarMovies(movieId = media.id)
        getProviderForMovies(movieId = media.id)
        getTrailerForMovies(movieId = media.id)
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
                providerDataObject = mapOf(
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
    data object Error : MovieState()
    data class Data(val mediaDetailObject: MovieDetailObject) : MovieState()
}

sealed class SimilarMovieState {
    data object Empty : SimilarMovieState()
    data object Error : SimilarMovieState()
    data class Data(val similarMoviesObject: List<MediaObject>) : SimilarMovieState()
}

sealed class ProviderState {
    data object Empty : ProviderState()
    data object Error : ProviderState()
    data class Data(val providerDataObject: Map<String, Result>) : ProviderState()
}

sealed class TrailerState {
    data object Empty : TrailerState()
    data object Error : TrailerState()
    data class Data(val trailerObject: TrailerObject) : TrailerState()
}
