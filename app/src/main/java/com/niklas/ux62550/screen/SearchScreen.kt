package com.niklas.ux62550.screen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.niklas.ux62550.LogoBox
import com.niklas.ux62550.PromoItem
import com.niklas.ux62550.R
import com.niklas.ux62550.ScreenWithGeneralNavBar
import com.niklas.ux62550.features.MovieBoxSearch.MovieBoxItemsUIState
import com.niklas.ux62550.models.MovieBoxItem
import com.niklas.ux62550.figmaPxToDp_h
import com.niklas.ux62550.figmaPxToDp_w
import com.niklas.ux62550.modules.HorizontalLazyRowWithSnapEffect
import com.niklas.ux62550.ui.theme.UX62550Theme


@Composable
@Preview(showBackground = true, name = "SearchPreview")
fun SearchPreview() {
    val movieBoxItems = listOf(
        MovieBoxItem("Name 1", R.drawable.logo, Color.Blue, "DINMOR", 4.5f),
        MovieBoxItem("Name 2", R.drawable.logo, Color.Red, "DINFAR", 4.5f)
    )

    UX62550Theme (darkTheme = true, dynamicColor = false) {
        ScreenWithGeneralNavBar {
            ScreenSearch(movieBoxItemsUIState = MovieBoxItemsUIState.Data(movieBoxItems))
        }
    }
}


@Composable
fun movieBoxMoviePicture(width: Dp, height: Dp, round: Dp, color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.clip(RoundedCornerShape(round)).background(color).size(width, height).padding()
    )
}

@Composable
fun MovieBoxRow(movieBoxItem: MovieBoxItem, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White, shape= RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {

        movieBoxMoviePicture(
            width = 90.dp,
            height = 50.62.dp,
            round = 12.dp,
            color = movieBoxItem.tempColor,
            modifier = Modifier.padding(end = 8.dp)
        )

        Column (
          modifier = Modifier
              .align(Alignment.CenterVertically)
              .padding(start = 8.dp)
        ) {
            Text(
                text = movieBoxItem.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = movieBoxItem.genre,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }


    }
}

@Composable
fun ScreenSearch(modifier: Modifier = Modifier, movieBoxItemsUIState: MovieBoxItemsUIState) {
    Column(modifier.padding()) {
        Row(
            modifier.fillMaxWidth().padding(figmaPxToDp_w(29.5f), figmaPxToDp_h(40f), 0.dp, figmaPxToDp_h(35f)), // ConvertPxToDp(29.5f)
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            //LogoBox(size= figmaPxToDp_w(50f))

            Box (modifier = Modifier.padding(figmaPxToDp_w(20f), 0.dp, 0.dp ,0.dp)) {

                Text(
                    text = "Welcome, User1",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Normal,
                    ),
                )

            }
        }



        // Implement viewmodel
        //val uiState = mediaItemsViewModel.mediaItemsState.collectAsState().value
        when (movieBoxItemsUIState) {
            MovieBoxItemsUIState.Empty -> {
                Text(
                    text = "No movies to be found",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            is MovieBoxItemsUIState.Data -> {
                // Display list of movie items in LazyColumn
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(movieBoxItemsUIState.movieBoxItems) { movieBoxItem ->
                        MovieBoxRow(movieBoxItem = movieBoxItem)
                    }
                }
            }
        }
    }
}
