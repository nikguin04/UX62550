package com.niklas.ux62550.domain

import com.niklas.ux62550.data.model.GenreDataObject
import com.niklas.ux62550.data.model.SearchDataObject
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class HomeRepository {

    private val homeDataSource = RemoteMediaDataSource()

    private val mutableFeaturedMediaFlow = MutableSharedFlow<SearchDataObject>()
    val featuredMediaFlow = mutableFeaturedMediaFlow.asSharedFlow()
    suspend fun getTrending(time_window: String = "day") = mutableFeaturedMediaFlow.emit(
        homeDataSource.getTrending(time_window)
    )

    private val mutableGenreFetchFlow = MutableSharedFlow<GenreDataObject>()
    val genreFetchFlow = mutableGenreFetchFlow.asSharedFlow()
    suspend fun getGenres() = mutableGenreFetchFlow.emit(
        homeDataSource.getMovieGenres()

    )




}
