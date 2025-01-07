package com.niklas.ux62550.domain

import com.niklas.ux62550.data.model.GenreDataObject
import com.niklas.ux62550.data.model.GenreType
import com.niklas.ux62550.data.model.SearchDataObject
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class HomeRepository {

    private val homeDataSource = RemoteMediaDataSource()

    private val mutableSearchFlow = MutableSharedFlow<SearchDataObject>()
    val searchFlow = mutableSearchFlow.asSharedFlow()
    suspend fun getSearch(search_mode: String, query: String) = mutableSearchFlow.emit(
        homeDataSource.getSearch(search_mode, query)
    )

    private val mutableGenreFetchFlow = MutableSharedFlow<GenreDataObject>()
    val genreFetchFlow = mutableGenreFetchFlow.asSharedFlow()
    suspend fun getGenres(type: GenreType) = mutableGenreFetchFlow.emit(
        when(type) {
            GenreType.MOVIE ->
                homeDataSource.getMovieGenres()
            GenreType.TV ->
                homeDataSource.getTvGenres()
            else ->
                throw Exception("Unimplemented genre type")
        }

    )




}
