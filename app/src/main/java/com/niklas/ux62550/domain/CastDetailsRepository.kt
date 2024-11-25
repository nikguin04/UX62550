package com.niklas.ux62550.domain

import com.niklas.ux62550.data.model.CastObject
import com.niklas.ux62550.data.model.SimilarMoviesObject
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class CastDetailsRepository {
    private val castDataSource = RemoteMediaDataSource()

    private val mutableCastFlow = MutableSharedFlow<CastObject>()
    val castFlow = mutableCastFlow.asSharedFlow()
    suspend fun getCasts(movie_id: Int)  = mutableCastFlow.emit(
        castDataSource.getCastDetails(movie_id))
}