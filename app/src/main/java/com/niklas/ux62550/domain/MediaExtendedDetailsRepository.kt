package com.niklas.ux62550.domain

import com.niklas.ux62550.data.model.ProviderDataObject
import com.niklas.ux62550.data.model.SearchDataObject
import com.niklas.ux62550.data.model.TrailerObject
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class MediaExtendedDetailsRepository(
    private val remoteDataSource: RemoteMediaDataSource
) {
    private val mutableSimilarMoviesFlow = MutableSharedFlow<Result<SearchDataObject>>()
    val similarFlow = mutableSimilarMoviesFlow.asSharedFlow()
    suspend fun getSimilarsMovies(movie_id: Int) = mutableSimilarMoviesFlow.emit(
        try { Result.success(remoteDataSource.getSimilarMoviesDetail(movie_id)) }
        catch (e: Exception) { Result.failure(e) }
    )

    private val mutableProviderFlow = MutableSharedFlow<Result<ProviderDataObject>>()
    val providerFlow = mutableProviderFlow.asSharedFlow()
    suspend fun getProvider(movie_id: Int) = mutableProviderFlow.emit(
        try { Result.success(remoteDataSource.getProviders(movie_id)) }
        catch (e: Exception) { Result.failure(e) }
    )

    private val mutableTrailerFlow = MutableSharedFlow<Result<TrailerObject>>()
    val trailerFlow = mutableTrailerFlow.asSharedFlow()
    suspend fun getTrailer(movie_id: Int) = mutableTrailerFlow.emit(
        try { Result.success(remoteDataSource.getTrailer(movie_id)) }
        catch (e: Exception) { Result.failure(e) }
    )
}
