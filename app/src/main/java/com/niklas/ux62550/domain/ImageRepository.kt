package com.niklas.ux62550.domain

import com.niklas.ux62550.data.model.ImagesDataObject
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class ImageRepository {
    private val remoteDataSource = RemoteMediaDataSource

    private val mutableDiscoverFlow = MutableSharedFlow<ImagesDataObject>()
    val imagesFlow = mutableDiscoverFlow.asSharedFlow()
    suspend fun getImages(media_type: String, media_id: Int,  include_image_language: String = "en")  = mutableDiscoverFlow.emit(
        remoteDataSource.getImages(media_type, media_id, include_image_language)
    )
}