package com.niklas.ux62550.domain

import com.niklas.ux62550.data.model.ImagesDataObject
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import com.niklas.ux62550.domain.common.KeyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ImageRepository (
    private val remoteDataSource: RemoteMediaDataSource
) : KeyRepository<Int, ImagesDataObject>() {

    suspend fun getImages(media_type: String, media_id: Int, include_image_language: String = "en"): ImagesDataObject {
        return remoteDataSource.getImages(media_type, media_id, include_image_language)
    }

}
