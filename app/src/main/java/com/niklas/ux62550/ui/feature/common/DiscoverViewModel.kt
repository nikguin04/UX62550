package com.niklas.ux62550.ui.feature.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.data.model.GenreObject
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.di.DataModule
import com.niklas.ux62550.domain.DiscoverKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DiscoverViewModel(private val genreObject: GenreObject) : ViewModel() {
    private val discoverRepository = DataModule.discoverRepository

    private val mutableDiscoverItemsState = MutableStateFlow<DiscoverItemsUIState>(DiscoverItemsUIState.Empty)
    val discoverItemsState: StateFlow<DiscoverItemsUIState> = mutableDiscoverItemsState

    var lastPage: Int = 1

    init {
        getDiscover(page = 1)
    }

    fun getDiscover(page: Int) {
        viewModelScope.launch {
            discoverRepository.getWithKey(
                itemKey = DiscoverKey(genreObject.id, page),
                getUnit = { (discoverRepository::getDiscoverMovies)(genreObject.id.toString(), page) },
                scope = viewModelScope
            ).collect { searchDataObjectResult ->
                // Append mediaType before updating data
                if (searchDataObjectResult.isSuccess) {
                    val searchDataObject = searchDataObjectResult.getOrThrow()
                    searchDataObject.results.forEach { res -> res.mediaType = "movie" }
                    // Either append to current data or make new data completely
                    when (discoverItemsState.value) {
                        is DiscoverItemsUIState.Data -> {
                            lastPage = page
                            mutableDiscoverItemsState.update {
                                DiscoverItemsUIState.Data(
                                    (discoverItemsState.value as DiscoverItemsUIState.Data).mediaObjects + searchDataObject.results
                                )
                            }
                        }
                        is DiscoverItemsUIState.Empty -> {
                            mutableDiscoverItemsState.update { DiscoverItemsUIState.Data(searchDataObject.results) }
                        }
                        is DiscoverItemsUIState.Error -> {
                            mutableDiscoverItemsState.update { DiscoverItemsUIState.Data(searchDataObject.results) }
                        }
                    }
                } else {
                    mutableDiscoverItemsState.update { DiscoverItemsUIState.Error }
                }
            }
        }
    }
}

class DiscoverViewModelFactory(private val genreObject: GenreObject) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return DiscoverViewModel(genreObject) as T
    }
}

sealed class DiscoverItemsUIState {
    data object Empty : DiscoverItemsUIState()
    data object Error : DiscoverItemsUIState()
    data class Data(val mediaObjects: List<MediaObject>) : DiscoverItemsUIState()
}
