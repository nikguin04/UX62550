package com.niklas.ux62550.domain

import com.niklas.ux62550.data.remote.RemoteFirebase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class WatchListRepository(
    private val firebaseDataSource: RemoteFirebase
) {
    private val mutableWatchListFlow = MutableSharedFlow<List<Int>?>()
    val watchListFlow = mutableWatchListFlow.asSharedFlow()
    suspend fun getWatchList() = firebaseDataSource.getWatchList(mutableWatchListFlow)
}
