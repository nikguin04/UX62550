package com.niklas.ux62550.domain

import com.niklas.ux62550.data.model.SearchDataObject
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class DiscoverRepository {

    private val remoteDataSource = RemoteMediaDataSource

    private val mutableDiscoverFlow = MutableSharedFlow<SearchDataObject>()
    val discoverFlow = mutableDiscoverFlow.asSharedFlow()
    suspend fun getDiscoverMovies(genres: String, page: Int)  = mutableDiscoverFlow.emit(
        remoteDataSource.getDiscoverMovies(genres, page)
    )

}
