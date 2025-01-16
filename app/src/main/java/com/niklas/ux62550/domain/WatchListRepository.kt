package com.niklas.ux62550.domain

import com.niklas.ux62550.data.model.WatchListDataObject
import com.niklas.ux62550.data.remote.RemoteFirebase
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class WatchListRepository(
    private val remoteDataSource: RemoteMediaDataSource,
    private val firebaseDataSource: RemoteFirebase
) {

    private val mutableWatchListFlow = MutableSharedFlow<List<Int>?>()
    val watchListFlow = mutableWatchListFlow.asSharedFlow()
    suspend fun getWatchList()  = firebaseDataSource.getWatchList(mutableWatchListFlow)

    private val mutableWatchListRowFlow = MutableSharedFlow<WatchListDataObject>()

    val watchListRowFlow = mutableWatchListRowFlow.asSharedFlow()
    suspend fun getMovieForRow(MovieId: Int)  = mutableWatchListRowFlow.emit(
        remoteDataSource.getMovieForRow(MovieId)
    )

}