package com.niklas.ux62550.domain

import com.niklas.ux62550.data.model.ImagesDataObject
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.data.model.MovieDetailObject
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import com.niklas.ux62550.domain.common.KeyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MediaDetailRepository(
    private val remoteDataSource: RemoteMediaDataSource
) : KeyRepository<Int, MovieDetailObject>() {
    suspend fun getMoviesDetails(movie_id: Int): MovieDetailObject {
        return remoteDataSource.getMoviesDetails(movie_id)
    }
}
