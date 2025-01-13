package com.niklas.ux62550.domain

import com.niklas.ux62550.data.model.GenreDataObject
import com.niklas.ux62550.data.model.GenreType
import com.niklas.ux62550.data.model.SearchDataObject
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import retrofit2.http.Path
import retrofit2.http.Query

class HomeRepository {

    private val homeDataSource = RemoteMediaDataSource()

    private val mutableFeaturedMediaFlow = MutableSharedFlow<SearchDataObject>()
    val featuredMediaFlow = mutableFeaturedMediaFlow.asSharedFlow()
    suspend fun getTrending(search_mode: String = "all", time_window: String = "day") = mutableFeaturedMediaFlow.emit(
        homeDataSource.getTrending(search_mode, time_window)
    )

    private val mutableGenreFetchFlow = MutableSharedFlow<GenreDataObject>()
    val genreFetchFlow = mutableGenreFetchFlow.asSharedFlow()
    suspend fun getGenres(type: GenreType) = mutableGenreFetchFlow.emit(
        when(type) {
            GenreType.MOVIE ->
                homeDataSource.getMovieGenres()
            else ->
                throw Exception("Unimplemented genre type")
        }

    )




}
