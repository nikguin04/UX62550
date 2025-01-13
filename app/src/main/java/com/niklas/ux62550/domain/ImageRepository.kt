package com.niklas.ux62550.domain

import com.niklas.ux62550.data.model.ImagesDataObject
import com.niklas.ux62550.data.model.SearchDataObject
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class ImageRepository {
    private val imageDataSource = RemoteMediaDataSource

    private val mutableDiscoverFlow = MutableSharedFlow<ImagesDataObject>()
    val imagesFlow = mutableDiscoverFlow.asSharedFlow()
    suspend fun getImages(media_type: String, media_id: Int,  include_image_language: String = "en")  = mutableDiscoverFlow.emit(
        imageDataSource.getImages(media_type, media_id, include_image_language)
    )
}