package com.niklas.ux62550.ui.feature.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.data.model.Cast
import com.niklas.ux62550.domain.CastDetailsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CastViewModel : ViewModel() {
    private val castDetailsRepository = CastDetailsRepository()

    private fun getCast() = viewModelScope.launch {
        castDetailsRepository.getCasts(205321) // TODO: Don't hardcore this, get some proper featured films
    }
    private val mutableCastState = MutableStateFlow<CastState>(CastState.Empty)
    val castState: StateFlow<CastState> = mutableCastState

    init {
        viewModelScope.launch {
            castDetailsRepository.castFlow.collect { castDetailObject ->
                mutableCastState.update {
                    CastState.Data(castDetailObject.cast)
                }
            }
        }
        getCast()
    }
}

sealed class CastState {
    data object Empty : CastState()
    data class Data(val castObject: List<Cast>) : CastState()
}