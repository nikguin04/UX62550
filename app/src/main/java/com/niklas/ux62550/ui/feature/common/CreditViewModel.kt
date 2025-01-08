package com.niklas.ux62550.ui.feature.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.data.model.CreditObject
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.domain.CastDetailsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreditViewModel(media: MediaObject) : ViewModel() {
    private val creditDetailsRepository = CastDetailsRepository()

    private fun getCredit(movieID: Int) = viewModelScope.launch {
        creditDetailsRepository.getCredits(movieID) // TODO: Don't hardcore this, get some proper featured films
    }
    private val mutableCreditState = MutableStateFlow<CreditState>(CreditState.Empty)
    val creditState: StateFlow<CreditState> = mutableCreditState

    init {
        viewModelScope.launch {
            creditDetailsRepository.creditFlow.collect { creditDetailObject ->
                mutableCreditState.update {
                    CreditState.Data(creditDetailObject)
                }
            }
        }
        getCredit(movieID = media.id)
    }
}
// TODO: change all names from cast to credits
class CreditsViewModelFactory(private val media: MediaObject) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T { return CreditViewModel(media) as T }
}

sealed class CreditState {
    data object Empty : CreditState()
    data class Data(val creditObject: CreditObject) : CreditState()
}