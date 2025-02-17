package com.niklas.ux62550.domain

import com.niklas.ux62550.data.model.SearchDataObject
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import com.niklas.ux62550.domain.common.KeyRepository

class DiscoverRepository(
    private val remoteDataSource: RemoteMediaDataSource
) : KeyRepository<DiscoverKey, SearchDataObject>() {
    suspend fun getDiscoverMovies(genres: String, page: Int): SearchDataObject {
        return remoteDataSource.getDiscoverMovies(genres, page)
    }
}

data class DiscoverKey(val genreId: Int, val page: Int)
