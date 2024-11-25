package com.niklas.ux62550.ui.feature.common

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
                mutableDiscoverItemsState.update { DiscoverItemsUIState.Data(searchDataObject.results) }
            }
        }
        getDiscover(genreObject.id.toString())
    }

    private fun getDiscover(genre_id: String) = viewModelScope.launch {
        discoverRepository.getDiscoverMovies(genre_id, 1) // TODO: Don't hardcore this, get some proper discovers
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
