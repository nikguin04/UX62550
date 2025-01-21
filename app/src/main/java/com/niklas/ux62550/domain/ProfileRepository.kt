package com.niklas.ux62550.domain

import com.niklas.ux62550.data.remote.RemoteFirebase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class ProfileRepository(
    private val firebaseDataSource: RemoteFirebase
) {

    private val mutableUserFlow = MutableSharedFlow<String>()
    val userFlow = mutableUserFlow.asSharedFlow()
    suspend fun getUserData()  = firebaseDataSource.getUserData(mutableUserFlow)
}