package com.niklas.ux62550.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.data.model.KeywordObject
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.di.DataModule
import com.niklas.ux62550.domain.KeywordRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class KeywordViewModel(private val keywordObject: KeywordObject) : ViewModel() {
    private val keywordRepository = DataModule.keywordRepository

    private val mutableKeywordItemsState = MutableStateFlow<KeywordItemsUIState>(KeywordItemsUIState.Empty)
    val keywordItemsState: StateFlow<KeywordItemsUIState> = mutableKeywordItemsState

    init {
        viewModelScope.launch {
            keywordRepository.keywordFlow.collect { searchDataObject ->
                mutableKeywordItemsState.update { KeywordItemsUIState.Data(searchDataObject.results) }
            }
        }
        getKeyword(keywordObject.id.toString())
    }

    private fun getKeyword(keyword_id: String) = viewModelScope.launch {
        keywordRepository.getKeywordSearch(keyword_id, 1) // TODO: Don't hardcore this, get some proper keywords
    }
}

class KeywordViewModelFactory(private val keywordObject: KeywordObject) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return KeywordViewModel(keywordObject) as T
    }
}

sealed class KeywordItemsUIState {
    data object Empty : KeywordItemsUIState()
    data class Data(val mediaObjects: List<MediaObject>) : KeywordItemsUIState()
}
