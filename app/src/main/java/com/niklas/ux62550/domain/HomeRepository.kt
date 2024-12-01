package com.niklas.ux62550.domain

import com.niklas.ux62550.data.model.SearchDataObject
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class HomeRepository {

    private val homeDataSource = RemoteMediaDataSource()

    private val mutableSearchFlow = MutableSharedFlow<SearchDataObject>()
    val searchFlow = mutableSearchFlow.asSharedFlow()
    suspend fun getSearch(search_mode: String, query: String)  = mutableSearchFlow.emit(
        homeDataSource.getSearch(search_mode, query)
    )




}
