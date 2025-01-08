package com.niklas.ux62550.ui.feature.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.data.model.Cast
import com.niklas.ux62550.data.model.CastObject
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.domain.CastDetailsRepository
import com.niklas.ux62550.ui.feature.mediadetails.MovieViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CastViewModel(media: MediaObject) : ViewModel() {
    private val castDetailsRepository = CastDetailsRepository()

    private fun getCast(movieID: Int) = viewModelScope.launch {
        castDetailsRepository.getCasts(movieID) // TODO: Don't hardcore this, get some proper featured films
    }
    private val mutableCastState = MutableStateFlow<CastState>(CastState.Empty)
    val castState: StateFlow<CastState> = mutableCastState

    init {
        viewModelScope.launch {
            castDetailsRepository.castFlow.collect { castDetailObject ->
                mutableCastState.update {
                    CastState.Data(castDetailObject)
                }
            }
        }
        getCast(movieID = media.id)
    }
}
// TODO: change all names from cast to credits
class CreditsViewModelFactory(private val media: MediaObject) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T { return CastViewModel(media) as T }
}

sealed class CastState {
    data object Empty : CastState()
    data class Data(val castObject: CastObject) : CastState()
}