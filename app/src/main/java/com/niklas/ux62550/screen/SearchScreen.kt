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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
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
import com.niklas.ux62550.R
import com.niklas.ux62550.ScreenWithGeneralNavBar
import com.niklas.ux62550.features.MovieBoxSearch.MovieBoxItemsUIState
import com.niklas.ux62550.features.MovieBoxSearch.NonMovieBoxItemsUIState
import com.niklas.ux62550.models.MovieBox
import com.niklas.ux62550.figmaPxToDp_h
import com.niklas.ux62550.figmaPxToDp_w
import com.niklas.ux62550.models.NonMovieBox
import com.niklas.ux62550.ui.theme.UX62550Theme


@Composable
@Preview(showBackground = true, name = "SearchPreview")
fun SearchPreview() {
    val movieBoxes = listOf(
        MovieBox("Name 1", R.drawable.logo, Color.Blue, "Movie", 3.5f),
        MovieBox("Name 2", R.drawable.logo, Color.Red, "Series", 4.5f)
    )
    val nonMovieBoxes = listOf(
        NonMovieBox("Name 1", R.drawable.logo, Color.Yellow, "Movie"),
        NonMovieBox("Name 2", R.drawable.logo, Color.Green, "Series")
    )

    UX62550Theme (darkTheme = true, dynamicColor = false) {
        ScreenWithGeneralNavBar {
            ScreenSearch(movieBoxItemsUIState = MovieBoxItemsUIState.Data(movieBoxes),
                nonMovieBoxItemsUIState = NonMovieBoxItemsUIState.Data(nonMovieBoxes))
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
fun nonMovieBoxMoviePicture(width: Dp, height: Dp, round: Dp, color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(color)
            .size(width, height)
            .padding()
    )
}

@Composable
fun MovieBoxRow(movieBox: MovieBox, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            //.background(Color.White, shape= RoundedCornerShape(8.dp))
            //.padding(8.dp)
    ) {

        movieBoxMoviePicture(
            width = 90.dp,
            height = 50.62.dp,
            round = 12.dp,
            color = movieBox.tempColor,
            modifier = Modifier.padding(end = 8.dp)
        )

        Column (
          modifier = Modifier
              //.fillMaxWidth()
              .align(Alignment.CenterVertically)
              .padding(start = 16.dp)
        ) {
            Row {
                Column {
                    Text(
                        text = movieBox.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = movieBox.rating.toString() + "/5",
                        //text =  "3/5",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = " | ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = movieBox.genre,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun NonMovieBoxRow(nonMovieBox: NonMovieBox, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
        //.background(Color.White, shape= RoundedCornerShape(8.dp))
        //.padding(8.dp)
    ) {

        nonMovieBoxMoviePicture(
            width = 40.dp,
            height = 40.dp,
            round = 12.dp,
            color = nonMovieBox.tempColor,
            modifier = Modifier.padding(end = 8.dp)
        )

        Row (
            modifier = Modifier
                //.fillMaxWidth()
                .align(Alignment.CenterVertically)
                .padding(start = 16.dp)
        ) {

            Text(
                text = nonMovieBox.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = " | ",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = nonMovieBox.genre,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ScreenSearch(modifier: Modifier = Modifier, movieBoxItemsUIState: MovieBoxItemsUIState,
                 nonMovieBoxItemsUIState: NonMovieBoxItemsUIState) {
    Column(modifier.padding()) {
        Row(
            modifier
                .fillMaxWidth()
                .padding(figmaPxToDp_w(0f), figmaPxToDp_h(40f), 0.dp, figmaPxToDp_h(35f)), // ConvertPxToDp(29.5f)
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

        Column() {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalDivider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(0.7f)
                )

                Text(
                    text = "Actors and Genres",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                    )
                )

                HorizontalDivider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(0.7f)
                )
            }
        }


        // Implement viewmodel
        //val uiState = mediaItemsViewModel.mediaItemsState.collectAsState().value
        when (nonMovieBoxItemsUIState) {
            NonMovieBoxItemsUIState.Empty -> {
                Text(
                    text = "No movies to be found",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            is NonMovieBoxItemsUIState.Data -> {
                // Display list of movie items in LazyColumn
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(nonMovieBoxItemsUIState.nonMovieBoxes) { nonMovieBoxItem ->
                        NonMovieBoxRow(nonMovieBox = nonMovieBoxItem)
                    }
                }
            }
        }

        Column() {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalDivider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(0.7f)
                )

                Text(
                    text = "Movies and Series",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                    )
                )

                HorizontalDivider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(0.7f)
                )
            }
        }



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
                    items(movieBoxItemsUIState.movieBoxes) { movieBoxItem ->
                        MovieBoxRow(movieBox = movieBoxItem)
                    }
                }
            }
        }

    }
}
