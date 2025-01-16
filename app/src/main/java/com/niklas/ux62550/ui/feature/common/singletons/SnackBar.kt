package com.niklas.ux62550.ui.feature.common.singletons

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope

class SnackBar {

    @Composable
    private fun attemptCreateSnackbarData() {
        SnackBar.scope = rememberCoroutineScope()
        SnackBar.snackbarHostState = remember { SnackbarHostState() }
    }

    @Composable
    fun getSnackBarScope(): CoroutineScope {
        attemptCreateSnackbarData()
        return SnackBar.scope!! // Assertive since it should be made in the attempt create, else we should throw an error
    }

    @Composable
    fun getSnackBarHostState(): SnackbarHostState {
        attemptCreateSnackbarData()
        return SnackBar.snackbarHostState!! // Assertive since it should be made in the attempt create, else we should throw an error
    }

    companion object {
        private var scope: CoroutineScope? = null
        private var snackbarHostState: SnackbarHostState? = null
    }
}