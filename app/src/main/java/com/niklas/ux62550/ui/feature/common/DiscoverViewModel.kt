package com.niklas.ux62550.ui.feature.common

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.data.model.GenreObject
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.domain.DiscoverRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DiscoverViewModel(private val genreObject: GenreObject) : ViewModel() {
    private val discoverRepository = DiscoverRepository()

    private val mutableDiscoverItemsState = MutableStateFlow<DiscoverItemsUIState>(DiscoverItemsUIState.Empty)
    val discoverItemsState: StateFlow<DiscoverItemsUIState> = mutableDiscoverItemsState

    init {
        viewModelScope.launch {
            discoverRepository.discoverFlow.collect { searchDataObject ->
                run { // Append media_type before updating data
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
                    }
                }
            }
        }
        getDiscover(genreObject.id.toString())
    }

    var lastGenreId: String? = null
    var lastPage: Int = 1
    fun getDiscover(genre_id: String, page: Int = 1) = viewModelScope.launch {
        lastGenreId = genre_id
        lastPage = page
        discoverRepository.getDiscoverMovies(genre_id, page)
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
}
