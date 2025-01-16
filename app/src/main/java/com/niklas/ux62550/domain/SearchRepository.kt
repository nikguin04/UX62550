package com.niklas.ux62550.domain


import com.niklas.ux62550.data.model.SearchDataObject

import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SearchRepository(
    private val remoteDataSource: RemoteMediaDataSource
) {

    private val mutableSearchFlow = MutableSharedFlow<SearchDataObject>()
    val SearchFlow = mutableSearchFlow.asSharedFlow()
    suspend fun getUserSearch(search_query: String)  = mutableSearchFlow.emit(
        remoteDataSource.getUserSearch(search_query)
    )
}