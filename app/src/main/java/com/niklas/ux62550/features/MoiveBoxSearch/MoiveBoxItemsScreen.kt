package com.niklas.ux62550.features.MoiveBoxSearch

import com.niklas.ux62550.features.MediaItemList.MediaItemsUIState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.niklas.ux62550.R
import com.niklas.ux62550.features.MediaItemList.MediaItemsList
import com.niklas.ux62550.models.MediaItem
import dk.shape.dtu.androidarchitecture.feature.fruitlist.MediaItemsScreen

@Composable
fun MovieBoxItemsScreen(MovieBoxItemsUIState: MoiveBoxItemsUIState) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        when (MoiveBoxItemsUIState) {
            MediaItemsUIState.Empty -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Fruit truck is delayed. Please wait.",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            is MediaItemsUIState.Data -> {
                MediaItemsList(
                    fruits = MoiveBoxItemsUIState.MoiveBoxItems,
                    modifier = Modifier.padding(innerPadding)
                )
            }

            else -> {

            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun MediaItemsScreenEmptyPreview() {
    MediaItemsScreen(
        MediaItemsUIState.Empty
    )
}

@Preview(showBackground = true)
@Composable
private fun FruitsScreenDataPreview() {
    val mediaItems = listOf(
        MediaItem("Name 1", R.drawable.logo, Color.Blue),
        MediaItem("Name 2", R.drawable.logo, Color.Red)
    )
    MediaItemsScreen(
        MediaItemsUIState.Data(mediaItems)
    )
}

