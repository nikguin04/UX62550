package com.niklas.ux62550.ui.feature.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niklas.ux62550.data.model.CreditObject
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.di.DataModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreditViewModel : ViewModel() {
    private val creditDetailsRepository = DataModule.castDetailsRepository

    private fun getCredit(movieId: Int) = viewModelScope.launch {
        creditDetailsRepository.getCredits(movieId) // TODO: Don't hardcore this, get some proper featured films
    }

    private val mutableCreditState = MutableStateFlow<CreditState>(CreditState.Empty)
    val creditState: StateFlow<CreditState> = mutableCreditState

    fun initPreview() {
        mutableCreditState.update {
            CreditState.Data(
                CreditObject(cast = listOf(), crew = listOf())
            )
        }
    }

    fun init(media: MediaObject) {
        viewModelScope.launch {
            creditDetailsRepository.creditsFlow.collect { creditDetailObject ->
                mutableCreditState.update {
                    if (creditDetailObject.isSuccess) { CreditState.Data(creditDetailObject.getOrThrow()) }
                    else { CreditState.Error }
                }
            }
        }
        getCredit(movieId = media.id)
    }
}

sealed class CreditState {
    data object Empty : CreditState()
    data object Error : CreditState()
    data class Data(val creditObject: CreditObject) : CreditState()
}
