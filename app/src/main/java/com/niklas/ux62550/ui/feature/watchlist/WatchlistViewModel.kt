package com.niklas.ux62550.ui.feature.watchlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.data.model.WatchListDataObject
import com.niklas.ux62550.di.DataModule
import com.niklas.ux62550.domain.WatchListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WatchlistViewModel() : ViewModel() {

    private val watchListRepository = DataModule.watchListRepository

    private fun getWatchList() = viewModelScope.launch {
        watchListRepository.getWatchList()
    }

    private val mutableWatchListState = MutableStateFlow<MovieIds>(MovieIds.Empty)
    val watchListState: StateFlow<MovieIds> = mutableWatchListState


    private val mutableWatchListRowState = MutableStateFlow<MovieIdsRow>(MovieIdsRow.Empty)
    val watchListRowState: StateFlow<MovieIdsRow> = mutableWatchListRowState


    init {
        viewModelScope.launch {
            watchListRepository.watchListFlow.collect { movieIdArray ->
                mutableWatchListState.update {
                    MovieIds.Data(movieIdArray)
                }
            }
        }

        viewModelScope.launch {
            watchListRepository.watchListRowFlow.collect { WatchListDataObject ->
                mutableWatchListRowState.update {
                    MovieIdsRow.Data(WatchListDataObject)
                }
            }
        }
// TODO make work
        getWatchList()
    }
}

sealed class MovieIds {
    data object Empty : MovieIds()
    data class Data(val movies: List<Int>?) : MovieIds()
    data object Error : MovieIds()
}

sealed class MovieIdsRow {
    data object Empty : MovieIdsRow()
    data class Data(val movies: WatchListDataObject) : MovieIdsRow()
    data object Error : MovieIdsRow()
}