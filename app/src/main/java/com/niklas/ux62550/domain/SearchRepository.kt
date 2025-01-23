package com.niklas.ux62550.domain

import com.niklas.ux62550.data.model.SearchDataObject

import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SearchRepository(
    private val remoteDataSource: RemoteMediaDataSource
) {
    private val mutableSearchFlow = MutableSharedFlow<Result<SearchDataObject>>()
    val SearchFlow = mutableSearchFlow.asSharedFlow()
    suspend fun getUserSearch(search_query: String) = mutableSearchFlow.emit(
        try { Result.success(remoteDataSource.getUserSearch(search_query)) }
        catch (e: Exception) { Result.failure(e) }
    )
}
