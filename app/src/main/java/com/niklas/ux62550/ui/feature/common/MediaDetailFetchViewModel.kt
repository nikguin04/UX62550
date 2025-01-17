package com.niklas.ux62550.ui.feature.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.data.model.GenreObject
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.data.model.MovieDetailObject
import com.niklas.ux62550.data.model.WatchListDataObject
import com.niklas.ux62550.di.DataModule
import com.niklas.ux62550.domain.DiscoverRepository
import com.niklas.ux62550.domain.MediaDetailsRepository
import com.niklas.ux62550.ui.feature.mediadetails.MovieState
import com.niklas.ux62550.ui.feature.watchlist.MovieIdsRow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MediaDetailFetchViewModel(movieId: Int): ViewModel() {
    private val mediaDetailsRepository = DataModule.mediaDetailsRepository

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
            getDetails(movieId)
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