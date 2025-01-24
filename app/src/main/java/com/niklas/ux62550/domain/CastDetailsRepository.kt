package com.niklas.ux62550.domain

import com.niklas.ux62550.data.model.CreditObject
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class CastDetailsRepository(
    private val remoteDataSource: RemoteMediaDataSource
) {
    private val mutableCreditsFlow = MutableSharedFlow<Result<CreditObject>>()
    val creditsFlow = mutableCreditsFlow.asSharedFlow()
    suspend fun getCredits(movieId: Int) = mutableCreditsFlow.emit(
        try { Result.success(remoteDataSource.getCreditDetails(movieId)) }
        catch (e: Exception) { Result.failure(e) }
    )
}
