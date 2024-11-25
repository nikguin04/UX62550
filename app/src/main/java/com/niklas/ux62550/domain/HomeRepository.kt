package com.niklas.ux62550.domain

import com.niklas.ux62550.data.model.SearchDataObject
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import com.niklas.ux62550.models.MediaItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class HomeRepository {

    private val homeDataSource = RemoteMediaDataSource()

    private val mutableSearchFlow = MutableSharedFlow<SearchDataObject>()
    val searchFlow = mutableSearchFlow.asSharedFlow()

    suspend fun getMultiSearch(query: String)  = mutableSearchFlow.emit(
        homeDataSource.getMultiSearch(query)
    )

}
