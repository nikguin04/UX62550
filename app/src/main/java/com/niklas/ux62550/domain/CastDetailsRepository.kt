package com.niklas.ux62550.domain

import com.niklas.ux62550.data.model.CreditObject
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class CastDetailsRepository(
    private val remoteDataSource: RemoteMediaDataSource
) {

    private val mutableCreditFlow = MutableSharedFlow<Result<CreditObject>>()
    val creditFlow = mutableCreditFlow.asSharedFlow()
    suspend fun getCredits(movie_id: Int)  = mutableCreditFlow.emit(
        try { Result.success( remoteDataSource.getCreditDetails(movie_id)) }
        catch (e: Exception) { Result.failure(e) }
    )
}