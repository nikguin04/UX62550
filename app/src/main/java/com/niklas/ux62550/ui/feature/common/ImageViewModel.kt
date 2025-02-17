package com.niklas.ux62550.ui.feature.common

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.data.model.ImageDataObject
import com.niklas.ux62550.data.model.ImagesDataObject
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.di.DataModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ImageViewModel(private val media: MediaObject) : ViewModel() {
    private val imageRepo = DataModule.imageRepository

    private val mutableImagesDataState = MutableStateFlow<ImagesDataUIState>(ImagesDataUIState.Empty)
    val imagesDataState: StateFlow<ImagesDataUIState> = mutableImagesDataState

    init {
        media.mediaType?.let {
            viewModelScope.launch {
                imageRepo.getWithKey(
                    itemKey = media.id,
                    getUnit = { (imageRepo::getImages)(it, media.id, "en") },
                    scope = viewModelScope
                ).collect { imagesObjResult ->
                    if (imagesObjResult.isSuccess) {
                        val imagesObj = imagesObjResult.getOrThrow()
                        mutableImagesDataState.update { ImagesDataUIState.Data(imagesObj) }
                    } else {
                        mutableImagesDataState.update { ImagesDataUIState.Error }
                    }
                }
            }
        } ?: Log.w("NO_MEDIA_TYPE", "Media passed to ImageViewModel contained no mediaType, so we can not fetch images, Please note that this sometimes need to be set manually when fetching data since endpoints for specific media will not include the mediaType")
    }

    fun initPreviewNoFetch() {
        mutableImagesDataState.update { ImagesDataUIState.Data(ImagesDataObject.EmptyExample) }
    }

    fun initPreview() {
        mutableImagesDataState.update { ImagesDataUIState.Data(ImagesDataObject.EmptyExample.copy(backdrops = listOf(ImageDataObject.EmptyExample))) }
    }
}

class ImageViewModelFactory(private val media: MediaObject) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return ImageViewModel(media) as T
    }
}

sealed class ImagesDataUIState {
    data object Empty : ImagesDataUIState()
    data object Error : ImagesDataUIState()
    data class Data(val media: ImagesDataObject) : ImagesDataUIState()
}
