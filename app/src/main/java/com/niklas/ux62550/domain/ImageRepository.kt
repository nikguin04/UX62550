package com.niklas.ux62550.domain

import com.niklas.ux62550.data.model.ImagesDataObject
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ImageRepository(
    private val remoteDataSource: RemoteMediaDataSource
) {

    private val mutableDiscoverFlow: MutableMap<Int, MutableSharedFlow<ImagesDataObject>> = mutableMapOf()
    val imagesFlow: MutableMap<Int, SharedFlow<ImagesDataObject>> = mutableMapOf()
    suspend fun getImages(media_type: String, media_id: Int, include_image_language: String = "en", scope: CoroutineScope): SharedFlow<ImagesDataObject> {
        if (imagesFlow.containsKey(media_id)) {
            return imagesFlow[media_id]!!
        }
        mutableDiscoverFlow[media_id] = MutableSharedFlow()
        imagesFlow[media_id] = mutableDiscoverFlow[media_id]!!.asSharedFlow()

        scope.launch {
            mutableDiscoverFlow[media_id]?.emit(
                remoteDataSource.getImages(media_type, media_id, include_image_language)
            )
        }
        return imagesFlow[media_id]!!
    }
}