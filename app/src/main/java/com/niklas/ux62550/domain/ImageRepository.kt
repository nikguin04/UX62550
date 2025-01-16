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
) : KeyRepository<ImagesDataObject>(remoteDataSource) {

    /*override fun <IN>
    scopeEmitFlow(itemId: Int, mutableItemFlow: MutableSharedFlow<ImagesDataObject>?, vararg inputs: IN) {
        scope.launch {
            mutableDiscoverFlow[media_id]?.emit(
                remoteDataSource.getImages(media_type, media_id, include_image_language)
            )
        }
    }*/

//    suspend fun scopeEmitFlow: (CoroutineScope, MutableSharedFlow<RemoteMediaDataSource>) -> Unit {
//
//    }

//    suspend fun test() {
//        val testvar: suspend() -> Unit = { (this::getImages)("a", 0, "en", scope) }
//    }

    suspend fun getImages(media_type: String, media_id: Int, include_image_language: String = "en"): ImagesDataObject {
        //mutableDiscoverFlow[media_id]?.emit(
            return remoteDataSource.getImages(media_type, media_id, include_image_language)
        //)
    }


    /*suspend fun getImages(media_type: String, media_id: Int, include_image_language: String = "en", scope: CoroutineScope): SharedFlow<ImagesDataObject> {

    }*/
}
