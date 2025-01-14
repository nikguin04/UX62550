package com.niklas.ux62550.domain

import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.data.model.SearchDataObject
import com.niklas.ux62550.data.remote.RemoteFirebase
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class ReviewListRepository {
    private val firebaseDataSource = RemoteFirebase()

    private val mutableWatchListFlow = MutableSharedFlow<List<Int>?>()
    val watchListFlow = mutableWatchListFlow.asSharedFlow()
    suspend fun getWatchList()  = firebaseDataSource.getWatchList(mutableWatchListFlow)

}