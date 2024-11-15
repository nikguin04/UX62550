package com.niklas.ux62550.ui.screen_search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.niklas.ux62550.R
import com.niklas.ux62550.models.MovieBox
import com.niklas.ux62550.models.NonMovieBox
import com.niklas.ux62550.models.figmaPxToDp_h
import com.niklas.ux62550.models.figmaPxToDp_w
import com.niklas.ux62550.ui.theme.SeachColorForText
import com.niklas.ux62550.ui.theme.UX62550Theme

@Composable
@Preview(showBackground = true, name = "SearchPreview")
fun SearchPreview() {
    val movieBoxes = listOf(
        MovieBox("Name 1", R.drawable.logo, Color.Blue, "Movie", 3.5f),
        MovieBox("Name 2", R.drawable.logo, Color.Red, "Series", 4.5f)
    )
    val nonMovieBoxes = listOf(
        NonMovieBox("someActor", R.drawable.logo, Color.Yellow, "Movie"),
        NonMovieBox("someGenre", R.drawable.logo, Color.Green, "Series")
    )

    UX62550Theme (darkTheme = true, dynamicColor = false) {
        Surface(modifier = Modifier.fillMaxSize()) {
            ScreenSearch(
                movieBoxItemsUIState = MovieBoxItemsUIState.Data(movieBoxes),
                nonMovieBoxItemsUIState = NonMovieBoxItemsUIState.Data(nonMovieBoxes),
                onNavigateToMedia = {}
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenSearch(
    modifier: Modifier = Modifier,
    movieBoxItemsUIState: MovieBoxItemsUIState,
    nonMovieBoxItemsUIState: NonMovieBoxItemsUIState,
    onNavigateToMedia: (String) -> Unit
) {
    Column(modifier.padding()) {
        Row(
            modifier
                .fillMaxWidth()
                .padding(figmaPxToDp_w(0f), figmaPxToDp_h(40f), 0.dp, figmaPxToDp_h(17.5f)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            //LogoBox(size= figmaPxToDp_w(50f))
            SearchBar(
                inputField = {
                    var text = ""
                    var expanded = false
                    SearchBarDefaults.InputField(query = text,
                        onQueryChange = { text = it },
                        onSearch = { expanded = false },
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        placeholder = { Text(text = "Search", color = Color(0xFF707070), fontSize = 12.sp) },
                        leadingIcon = {
                            Image( //Needs to be made button
                                imageVector = Icons.Filled.Search,
                                modifier = Modifier.requiredSize(24.dp),
                                colorFilter = ColorFilter.tint(Color.Black),
                                contentDescription = "Star icon"
                            )
                        }
                    )

                },
                colors = SearchBarDefaults.colors(containerColor = Color(0xFFACACAC)),
                expanded = false,
                onExpandedChange = {},
                content = {},

                modifier = Modifier.padding(0.dp,0.dp,0.dp,20.dp)
            )
        }


        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalDivider(
                color = SeachColorForText,
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
                    //fontFamily = "Roboto",
                    color = SeachColorForText
                )
            )

            HorizontalDivider(
                color = SeachColorForText,
                thickness = 1.dp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(0.7f)
            )
        }


        // Implement viewmodel
        //val uiState = mediaItemsViewModel.mediaItemsState.collectAsState().value
        when (nonMovieBoxItemsUIState) {
            NonMovieBoxItemsUIState.Empty -> {
                Text(
                    text = "No movies to be found",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = SeachColorForText
                )
            }
            is NonMovieBoxItemsUIState.Data -> {
                // Display list of movie items in LazyColumn
                LazyColumn(modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                    var FirstTimeRun = 0;
                    items(nonMovieBoxItemsUIState.nonMovieBoxes) { nonMovieBoxItem ->
                        if(FirstTimeRun == 0){
                            FirstTimeRun = 1;
                        } else {
                            HorizontalDivider(
                                color = SeachColorForText,
                                thickness = 1.dp,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth(0.7f)
                            )
                        }
                        NonMovieBoxRow(nonMovieBox = nonMovieBoxItem)
                    }

                }
            }
        }


        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalDivider(
                color = SeachColorForText,
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
                    color = SeachColorForText,
                )
            )

            HorizontalDivider(
                color = SeachColorForText,
                thickness = 1.dp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(0.7f)
            )

        }



        when (movieBoxItemsUIState) {
            MovieBoxItemsUIState.Empty -> {
                Text(
                    text = "No movies to be found",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = SeachColorForText
                )
            }
            is MovieBoxItemsUIState.Data -> {
                // Display list of movie items in LazyColumn
                LazyColumn(modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    var FirstTimeRun = 0;
                    items(movieBoxItemsUIState.movieBoxes) { movieBoxItem ->
                        if(FirstTimeRun == 0){
                            FirstTimeRun = 1;
                        } else {
                            HorizontalDivider(
                                color = SeachColorForText,
                                thickness = 1.dp,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth(0.7f)
                            )
                        }
                        MovieBoxRow(
                            movieBox = movieBoxItem,
                            modifier = Modifier.clickable(
                                onClick = { onNavigateToMedia(movieBoxItem.name) }
                            )
                        )
                    }
                }
            }
        }

    }
}
