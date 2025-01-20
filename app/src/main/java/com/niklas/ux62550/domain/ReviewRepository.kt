package com.niklas.ux62550.domain

import com.niklas.ux62550.data.remote.RemoteFirebase
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class ReviewRepository(
    private val firebaseDataSource: RemoteFirebase
) {

    private val mutableReviewFlow = MutableSharedFlow<List<Int>?>()
    val reviewFlow = mutableReviewFlow.asSharedFlow()

}