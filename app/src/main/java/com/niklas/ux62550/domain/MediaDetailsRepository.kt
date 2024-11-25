package com.niklas.ux62550.domain

import com.niklas.ux62550.data.model.MovieDetailObject;
import com.niklas.ux62550.data.remote.RemoteMediaDataSource;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.asSharedFlow

class MediaDetailsRepository {

    private val detailsDataSource = RemoteMediaDataSource()

    private val mutableDetailFlow = MutableSharedFlow<MovieDetailObject>()
    val detailFlow = mutableDetailFlow.asSharedFlow()
    suspend fun getMoviesDetails(movie_id: Int)  = mutableDetailFlow.emit(
        detailsDataSource.getMoviesDetails(movie_id)
    )
}
