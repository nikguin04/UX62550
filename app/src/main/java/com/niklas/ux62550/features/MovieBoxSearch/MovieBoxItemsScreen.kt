package com.niklas.ux62550.features.MovieBoxSearch

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
import com.niklas.ux62550.models.MovieBoxItem

@Composable
fun MovieBoxItemsScreen(movieBoxItemsUIState: MovieBoxItemsUIState) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        when (movieBoxItemsUIState) {
            movieBoxItemsUIState -> {
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

            is MovieBoxItemsUIState.Data -> {
                MovieBoxItemsList(
                    fruits = movieBoxItemsUIState.movieBoxItems,
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
private fun MovieBoxItemsScreenEmptyPreview() {
    MovieBoxItemsScreen(
        MovieBoxItemsUIState.Empty
    )
}

@Preview(showBackground = true)
@Composable
private fun FruitsScreenDataPreview() {
    val MovieBoxItems = listOf(
        MovieBoxItem("Name 1", R.drawable.logo, Color.Blue, genre="Lol", rating=3f),
        MovieBoxItem("Name 2", R.drawable.logo, Color.Red, genre="Lol", rating=3f)
    )
    MovieBoxItemsScreen(
        MovieBoxItemsUIState.Data(MovieBoxItems)
    )
}

