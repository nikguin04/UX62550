package com.niklas.ux62550.domain

import com.niklas.ux62550.data.model.SearchDataObject
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class KeywordRepository {

    private val keywordDataSource = RemoteMediaDataSource

    private val mutableKeywordFlow = MutableSharedFlow<SearchDataObject>()
    val keywordFlow = mutableKeywordFlow.asSharedFlow()
    suspend fun getKeywordSearch(keyword_id: String, page: Int)  = mutableKeywordFlow.emit(
        keywordDataSource.getKeywordMovies(keyword_id, page)
    )

}
