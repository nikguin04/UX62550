package com.niklas.ux62550.domain

import com.niklas.ux62550.data.model.MovieDetailObject
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import com.niklas.ux62550.domain.common.KeyRepository

class MediaDetailRepository(
    private val remoteDataSource: RemoteMediaDataSource
) : KeyRepository<Int, MovieDetailObject>() {
    suspend fun getMoviesDetails(movie_id: Int): MovieDetailObject {
        return remoteDataSource.getMoviesDetails(movie_id)
    }
}
