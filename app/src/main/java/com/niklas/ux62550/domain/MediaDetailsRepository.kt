package com.niklas.ux62550.domain

import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.data.model.MovieDetailObject
import com.niklas.ux62550.data.model.ProviderDataObject
import com.niklas.ux62550.data.model.SearchDataObject
import com.niklas.ux62550.data.model.TrailerObject
import com.niklas.ux62550.data.remote.RemoteFirebase
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class MediaDetailsRepository(
    private val remoteDataSource: RemoteMediaDataSource,
    private val firebaseDataSource: RemoteFirebase
) {
    private val mutableDetailFlow = MutableSharedFlow<MovieDetailObject>()
    val detailFlow = mutableDetailFlow.asSharedFlow()
    suspend fun getMoviesDetails(movie_id: Int)  = mutableDetailFlow.emit(
        remoteDataSource.getMoviesDetails(movie_id)
    )

    private val mutableSimilarMoviesFlow = MutableSharedFlow<SearchDataObject>()
    val similarFlow = mutableSimilarMoviesFlow.asSharedFlow()
    suspend fun getSimilarsMovies(movie_id: Int)  = mutableSimilarMoviesFlow.emit(
        remoteDataSource.getSimilarMoviesDetail(movie_id)

    )

    private val mutableProviderFlow = MutableSharedFlow<ProviderDataObject>()
    val providerFlow = mutableProviderFlow.asSharedFlow()
    suspend fun getProvider(movie_id: Int)  = mutableProviderFlow.emit(
        remoteDataSource.getProviders(movie_id)

    )
    private val mutableTrailerFlow = MutableSharedFlow<TrailerObject>()
    val trailerFlow = mutableTrailerFlow.asSharedFlow()
    suspend fun getTrailer(movie_id: Int)  = mutableTrailerFlow.emit(
        remoteDataSource.getTrailer(movie_id)

    )



}
