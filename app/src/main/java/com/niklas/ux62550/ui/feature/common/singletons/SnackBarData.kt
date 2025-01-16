package com.niklas.ux62550.ui.feature.common.singletons

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope

class SnackBarData(scope: CoroutineScope, snackbarHostState: SnackbarHostState) {
    val scope: CoroutineScope = scope
    val snackBarHostState: SnackbarHostState = snackbarHostState
}