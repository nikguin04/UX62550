package com.niklas.ux62550.domain

import com.niklas.ux62550.data.model.GenreDataObject
import com.niklas.ux62550.data.model.SearchDataObject
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class HomeRepository (
    private val remoteDataSource: RemoteMediaDataSource
) {

    private val mutableFeaturedMediaFlow = MutableSharedFlow<Result<SearchDataObject>>()
    val featuredMediaFlow = mutableFeaturedMediaFlow.asSharedFlow()
    suspend fun getTrending(time_window: String = "day") = mutableFeaturedMediaFlow.emit(
        try { Result.success(remoteDataSource.getTrending(time_window)) }
        catch (e: Exception) { Result.failure(e)  }
    )

    private val mutableGenreFetchFlow = MutableSharedFlow<Result<GenreDataObject>>()
    val genreFetchFlow = mutableGenreFetchFlow.asSharedFlow()
    suspend fun getGenres() = mutableGenreFetchFlow.emit(
        try { Result.success(remoteDataSource.getMovieGenres()) }
        catch (e: Exception) { Result.failure(e)  }

    )




}

