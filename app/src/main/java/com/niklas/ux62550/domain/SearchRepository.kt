package com.niklas.ux62550.domain

import com.niklas.ux62550.data.model.SearchDataObject

import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SearchRepository(
    private val remoteDataSource: RemoteMediaDataSource
) {
    private val mutableSearchFlow = MutableSharedFlow<Result<SearchDataObject>>()
    val searchFlow = mutableSearchFlow.asSharedFlow()
    suspend fun getUserSearch(searchQuery: String) = mutableSearchFlow.emit(
        try { Result.success(remoteDataSource.getUserSearch(searchQuery)) }
        catch (e: Exception) { Result.failure(e) }
    )
}
