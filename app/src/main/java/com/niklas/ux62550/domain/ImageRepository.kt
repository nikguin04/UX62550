package com.niklas.ux62550.domain

import com.niklas.ux62550.data.model.ImagesDataObject
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import com.niklas.ux62550.domain.common.KeyRepository

class ImageRepository(
    private val remoteDataSource: RemoteMediaDataSource
) : KeyRepository<Int, ImagesDataObject>() {
    suspend fun getImages(mediaType: String, mediaId: Int, includeImageLanguage: String = "en"): ImagesDataObject {
        return remoteDataSource.getImages(mediaType, mediaId, includeImageLanguage)
    }
}
