package com.niklas.ux62550.ui.feature.common.singletons

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope

class SnackBarData(scope: CoroutineScope, snackbarHostState: SnackbarHostState) {
    private val scope: CoroutineScope
    private val snackBarHostState: SnackbarHostState

    init { // NOTE: this "singleton" should always be able to be rewritten due to the fact that android re-renders the main screen, and therefore changes the scope at runtime
        this.scope = scope
        this.snackBarHostState = snackbarHostState
        SnackBarData.singleton = this
    }

    @Composable
    fun getSnackBarScope(): CoroutineScope {
        return scope // Assertive since it should be made in the attempt create, else we should throw an error
    }

    @Composable
    fun getSnackBarHostState(): SnackbarHostState {
        return snackBarHostState // Assertive since it should be made in the attempt create, else we should throw an error
    }

    companion object {
        private var singleton: SnackBarData? = null
        fun get(): SnackBarData? { return singleton }
    }
}