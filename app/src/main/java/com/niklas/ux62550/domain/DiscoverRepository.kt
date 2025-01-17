package com.niklas.ux62550.domain

import com.niklas.ux62550.data.model.ImagesDataObject
import com.niklas.ux62550.data.model.SearchDataObject
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import com.niklas.ux62550.domain.common.KeyRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class DiscoverRepository(
    private val remoteDataSource: RemoteMediaDataSource
) : KeyRepository<SearchDataObject>(remoteDataSource) {

    suspend fun getDiscoverMovies(genres: String, page: Int): SearchDataObject {
        return remoteDataSource.getDiscoverMovies(genres, page)
    }

}
