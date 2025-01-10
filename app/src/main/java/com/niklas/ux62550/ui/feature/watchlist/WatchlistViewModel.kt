package com.niklas.ux62550.ui.feature.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.data.model.MovieDetailObject
import com.niklas.ux62550.data.model.SearchDataObject
import com.niklas.ux62550.data.model.WatchListDataObject
import com.niklas.ux62550.domain.WatchListRepository
import com.niklas.ux62550.ui.feature.mediadetails.MovieState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.connection.reorderForHappyEyeballs

class WatchlistViewModel() : ViewModel() {

    private val watchListRepository = WatchListRepository()

    private fun getWatchList() = viewModelScope.launch {
        watchListRepository.getWatchList()
    }

    private fun getWatchListRow(MovieId: Int) = viewModelScope.launch {
        watchListRepository.getMovieForRow(MovieId)
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
        when(getWatchList()){
            MovieIds.Data -> Id{
                getWatchListRow(MovieId = watchListState.value )
            }

        }



    }

//    fun initPreview() {
//
//        mutableMovieState.update {
//            MovieState.Data(
//                mediaDetailObjects = MovieDetailObject()
//            )
//        }
//    }
}

sealed class MovieIds {
    data object Empty : MovieIds()
    data class Data(val movies: List<Int>?) : MovieIds()
}

sealed class MovieIdsRow {
    data object Empty : MovieIdsRow()
    data class Data(val movies: WatchListDataObject) : MovieIdsRow()
}