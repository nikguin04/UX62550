package com.niklas.ux62550.ui.feature.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.data.model.GenreObject
import com.niklas.ux62550.data.model.ImagesDataObject
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.data.remote.RemoteMediaDataSource
import com.niklas.ux62550.di.DataModule
import com.niklas.ux62550.domain.DiscoverKey
import com.niklas.ux62550.domain.DiscoverRepository
import com.niklas.ux62550.domain.common.KeyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class DiscoverViewModel(private val genreObject: GenreObject) : ViewModel() {
    private val discoverRepository = DataModule.discoverRepository

    private val mutableDiscoverItemsState = MutableStateFlow<DiscoverItemsUIState>(DiscoverItemsUIState.Empty)
    val discoverItemsState: StateFlow<DiscoverItemsUIState> = mutableDiscoverItemsState

    var lastPage: Int = 1

    init {
        //getDiscover(page = 1)
    }


    fun getDiscover(page: Int) {
        lastPage = page
        viewModelScope.launch {
            discoverRepository.getWithKey(
                itemKey = DiscoverKey(genreObject.id, page),
                getUnit = { (discoverRepository::getDiscoverMovies)(genreObject.id.toString(), page) },
                scope = viewModelScope
            ).collect { searchDataObject ->
                // Append media_type before updating data
                searchDataObject.results.forEach { res -> res.media_type = "movie" }
                // Either append to current data or make new data completely
                when (discoverItemsState.value) {
                    is DiscoverItemsUIState.Data -> {
                        mutableDiscoverItemsState.update {
                            DiscoverItemsUIState.Data(
                                (discoverItemsState.value as DiscoverItemsUIState.Data).mediaObjects + searchDataObject.results
                            )
                        }
                    }
                    DiscoverItemsUIState.Empty -> {
                        mutableDiscoverItemsState.update { DiscoverItemsUIState.Data(searchDataObject.results) }
                    }
                    DiscoverItemsUIState.Error -> {
                        mutableDiscoverItemsState.update { DiscoverItemsUIState.Data(searchDataObject.results) }
                    }
                }
            }
        }
    }
}

class DiscoverViewModelFactory(private val genreObject: GenreObject) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DiscoverViewModel(genreObject) as T
    }
}

sealed class DiscoverItemsUIState {
    data object Empty : DiscoverItemsUIState()
    data class Data(val mediaObjects: List<MediaObject>) : DiscoverItemsUIState()
    data object Error : DiscoverItemsUIState()
}

