package com.niklas.ux62550.features.MovieBoxSearch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.niklas.ux62550.R
import com.niklas.ux62550.models.MediaItem
import com.niklas.ux62550.models.MovieBoxItem
import com.niklas.ux62550.ui.theme.UX62550Theme

@Composable
fun MovieBoxItemsList(fruits: List<MovieBoxItem>, modifier: Modifier = Modifier) {
    val scrollState = rememberLazyListState()

    LaunchedEffect(fruits) {
        scrollState.scrollToItem(0)
    }

    LazyColumn(
        state = scrollState,
        modifier = modifier
            .fillMaxSize()
    ) {
        itemsIndexed(fruits) { index, fruit ->
            FruitItem(fruit = fruit, index = index)
        }
    }
}

@Composable
private fun FruitItem(fruit: MovieBoxItem, index: Int = 0) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "$index ${fruit.name}",
            fontSize = 25.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = fruit.backdrop.toString(),
            fontSize = 17.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FruitListPreview() {
    UX62550Theme {
        MovieBoxItemsList(
            listOf(MovieBoxItem("TEST NAME", R.drawable.ic_launcher_background, Color.Black, genre="Lol", rating=3f))
        )
    }
}
