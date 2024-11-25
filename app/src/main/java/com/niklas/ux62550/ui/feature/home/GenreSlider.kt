package com.niklas.ux62550.ui.feature.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.niklas.ux62550.data.model.KeywordObject


@Composable
fun KeywordSlider(keywordViewModel: KeywordViewModel, keywordObject: KeywordObject, onNavigateToMedia: (String) -> Unit) {
    val keywordUiState = keywordViewModel.keywordItemsState.collectAsState().value

    when (keywordUiState) {
        KeywordItemsUIState.Empty -> {
            Text(
                text = "No Keyword Items",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        is KeywordItemsUIState.Data -> {
            Row(
                Modifier.padding(
                    15.dp,
                    12.dp,
                    0.dp,
                    2.dp
                )
            ) {
                Text(text = keywordObject.name)
            }
            HorizontalLazyRowMovies(
                Modifier.padding(0.dp, 0.dp),
                155f,
                87.19f,
                keywordUiState.mediaObjects,
                onNavigateToMedia
            )
        }
        else -> {}
    }


}
