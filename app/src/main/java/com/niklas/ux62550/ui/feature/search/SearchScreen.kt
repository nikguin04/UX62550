package com.niklas.ux62550.ui.feature.search

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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.niklas.ux62550.ui.theme.SearchColorForText
import com.niklas.ux62550.ui.theme.UX62550Theme

@Composable
@Preview(showBackground = true)
fun SearchPreview() {
    UX62550Theme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            SearchScreen(onNavigateToMedia = {})
        }
    }
}

@Composable
fun SearchScreen(viewModel: SearchViewModel = viewModel(), onNavigateToMedia: (String) -> Unit) {
    val nonMoviesState = viewModel.nonMoviesState.collectAsState().value
    val moviesState = viewModel.moviesState.collectAsState().value
    SearchContent(movieItemsUIState = moviesState, nonMovieBoxItemsUIState = nonMoviesState, onNavigateToMedia = onNavigateToMedia)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchContent(
    modifier: Modifier = Modifier,
    movieItemsUIState: MovieItemsUIState,
    nonMovieBoxItemsUIState: NonMovieBoxItemsUIState,
    onNavigateToMedia: (String) -> Unit
) {
    Column(modifier = modifier.padding()) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(0.dp, 45.dp, 0.dp, 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            SearchBar(
                inputField = {
                    var text = ""
                    var expanded = false
                    SearchBarDefaults.InputField(query = text,
                        onQueryChange = { text = it },
                        onSearch = { expanded = false },
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        placeholder = {
                            Text(
                                text = "Search",
                                color = Color(0xFF707070),
                                fontSize = 12.sp
                            )
                        },
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
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 20.dp)
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalDivider(
                color = SearchColorForText,
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
                    color = SearchColorForText
                )
            )

            HorizontalDivider(
                color = SearchColorForText,
                thickness = 1.dp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(0.7f)
            )
        }

        // Implement ViewModel
        when (nonMovieBoxItemsUIState) {
            NonMovieBoxItemsUIState.Empty -> {
                Text(
                    text = "No movies to be found",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = SearchColorForText
                )
            }

            is NonMovieBoxItemsUIState.Data -> {
                // Display list of movie items in LazyColumn
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var putDivider = false;
                    items(nonMovieBoxItemsUIState.nonMovieBoxes) { nonMovieBoxItem ->
                        if (!putDivider) {
                            putDivider = true;
                        } else {
                            HorizontalDivider(
                                color = SearchColorForText,
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
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalDivider(
                color = SearchColorForText,
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
                    color = SearchColorForText,
                )
            )

            HorizontalDivider(
                color = SearchColorForText,
                thickness = 1.dp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(0.7f)
            )
        }

        when (movieItemsUIState) {
            MovieItemsUIState.Empty -> {
                Text(
                    text = "No movies to be found",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = SearchColorForText
                )
            }

            is MovieItemsUIState.Data -> {
                // Display list of movie items in LazyColumn
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var putDivider = false;
                    items(movieItemsUIState.movies) { movieBoxItem ->
                        if (!putDivider) {
                            putDivider = true;
                        } else {
                            HorizontalDivider(
                                color = SearchColorForText,
                                thickness = 1.dp,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth(0.7f)
                            )
                        }
                        MovieBoxRow(
                            movie = movieBoxItem,
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
