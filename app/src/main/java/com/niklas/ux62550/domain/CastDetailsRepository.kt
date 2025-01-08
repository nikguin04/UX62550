package com.niklas.ux62550.domain

import com.niklas.ux62550.data.model.CreditObject
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class CastDetailsRepository {
    private val creditDataSource = RemoteMediaDataSource()

    private val mutableCreditFlow = MutableSharedFlow<CreditObject>()
    val creditFlow = mutableCreditFlow.asSharedFlow()
    suspend fun getCredits(movie_id: Int)  = mutableCreditFlow.emit(
        creditDataSource.getCreditDetails(movie_id))
}