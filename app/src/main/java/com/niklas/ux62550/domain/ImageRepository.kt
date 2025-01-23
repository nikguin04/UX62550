package com.niklas.ux62550.domain

import com.niklas.ux62550.data.model.ImagesDataObject
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import com.niklas.ux62550.domain.common.KeyRepository

class ImageRepository(
    private val remoteDataSource: RemoteMediaDataSource
) : KeyRepository<Int, ImagesDataObject>() {
    suspend fun getImages(media_type: String, media_id: Int, include_image_language: String = "en"): ImagesDataObject {
        return remoteDataSource.getImages(media_type, media_id, include_image_language)
    }
}
