package com.niklas.ux62550.ui.feature.common

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.data.model.GenreObject
import com.niklas.ux62550.data.model.ImageDataObject
import com.niklas.ux62550.data.model.ImagesDataObject
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.di.DataModule
import com.niklas.ux62550.domain.DiscoverRepository
import com.niklas.ux62550.domain.ImageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

class ImageViewModel(private val media: MediaObject) : ViewModel() {
    private val imageRepo = DataModule.imageRepository

    private val mutableImagesDataState = MutableStateFlow<ImagesDataUIState>(ImagesDataUIState.Empty)
    val imagesDataState: StateFlow<ImagesDataUIState> = mutableImagesDataState

    init {
        media.media_type?.let {
            viewModelScope.launch {
                while (!imageRepo.imagesFlow.containsKey(media.id)) { yield() }
                imageRepo.imagesFlow[media.id]?.collect { imagesObj ->
                    mutableImagesDataState.update { ImagesDataUIState.Data(imagesObj) }
                }
            }
            getImages(it, media.id)
        } ?: Log.w("No media_type", "Media passed to ImageViewModel contained no media_type, so we can not fetch images, Please note that this sometimes need to be set manually when fetching data since endpoints for specific media will not include the media_type")
    }

    private fun getImages(media_type: String, media_id: Int) = viewModelScope.launch {
        imageRepo.getImages(media_type = media_type, media_id = media_id) // TODO: Don't hardcore this, get some proper discovers
    }

    fun initPreview_NoFetch() {
        mutableImagesDataState.update { ImagesDataUIState.Data(ImagesDataObject.EmptyExample) }
    }
    fun initPreview() {
        mutableImagesDataState.update { ImagesDataUIState.Data(ImagesDataObject.EmptyExample.copy(backdrops = listOf(ImageDataObject.EmptyExample))) }
    }
}

class ImageViewModelFactory(private val media: MediaObject) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ImageViewModel(media) as T
    }
}

sealed class ImagesDataUIState {
    data object Empty : ImagesDataUIState()
    data class Data(val media: ImagesDataObject) : ImagesDataUIState()
}