package com.niklas.ux62550.ui.feature.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.di.DataModule
import com.niklas.ux62550.ui.feature.mediadetails.MovieState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MediaDetailFetchViewModel(movieId: Int) : ViewModel() {
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
                    mutableMovieState.update { MovieState.Data(movieDetailObjectResult.getOrThrow()) }
                } else {
                    mutableMovieState.update { MovieState.Error }
                }
            }
        }
    }
}

class MediaDetailFetchViewModelFactory(private val movieId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MediaDetailFetchViewModel(movieId) as T
    }
}
