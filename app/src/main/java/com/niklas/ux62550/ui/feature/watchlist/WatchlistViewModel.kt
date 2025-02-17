package com.niklas.ux62550.ui.feature.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.di.DataModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WatchlistViewModel : ViewModel() {
    private val watchListRepository = DataModule.watchListRepository

    private fun getWatchList() = viewModelScope.launch {
        watchListRepository.getWatchList()
    }

    private val mutableWatchListState = MutableStateFlow<MovieIds>(MovieIds.Empty)
    val watchListState: StateFlow<MovieIds> = mutableWatchListState

    fun init() {
        viewModelScope.launch {
            watchListRepository.watchListFlow.collect { movieIdArray ->
                mutableWatchListState.update {
                    MovieIds.Data(movieIdArray)
                }
            }
        }

        getWatchList()
    }
}

sealed class MovieIds {
    data object Empty : MovieIds()
    data object Error : MovieIds()
    data class Data(val movies: List<Int>?) : MovieIds()
}
