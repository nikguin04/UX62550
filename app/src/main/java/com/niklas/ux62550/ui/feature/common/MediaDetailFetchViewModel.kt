package com.niklas.ux62550.ui.feature.common

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.data.model.MovieDetailObject
import com.niklas.ux62550.di.DataModule
import com.niklas.ux62550.ui.feature.mediadetails.MovieState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MediaDetailFetchViewModel(movieId: Int): ViewModel() {
    private val mediaDetailsRepository = DataModule.mediaDetailRepository

    private val mutableMovieState = MutableStateFlow<MovieState>(MovieState.Empty)
    val movieState: StateFlow<MovieState> = mutableMovieState

        init {
            viewModelScope.launch {
                mediaDetailsRepository.getWithKey(
                    itemKey = movieId,
                    getUnit = { (mediaDetailsRepository::getMoviesDetails)(movieId) },
                    scope = viewModelScope
                ).collect { movieDetailObjectResult ->
                    if (movieDetailObjectResult.isSuccess) {
                        MovieState.Data(movieDetailObjectResult.getOrThrow())
                    } else {
                        MovieState.Error
                    }

                }
            }
        }

    private fun getDetails(MovieID: Int) = viewModelScope.launch {
        mediaDetailsRepository.getMoviesDetails(MovieID) // TODO: Don't hardcore this, get some proper featured films
    }


    class MediaDetailFetchViewModelFactory(private val movieId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MediaDetailFetchViewModel(movieId) as T
        }
    }

    sealed class MediaDetailState {
        data object Empty : MediaDetailState()
        data class Data(val movieDetailObject: MovieDetailObject) : MediaDetailState()
        data object Error : MediaDetailState()
    }
}