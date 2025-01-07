package com.niklas.ux62550.ui.feature.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
    val moviesState = viewModel.movieItemsUIState.collectAsState().value
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
    var text by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    LazyColumn(modifier = modifier.padding()) {
        item {
            Row(
                modifier = Modifier

                    .padding(20.dp, 45.dp, 20.dp, 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                SearchBar(
                    inputField = {
                        SearchBarDefaults.InputField(
                            query = text,
                            onQueryChange = { text = it},
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
                                Image(
                                    imageVector = Icons.Filled.Search,
                                    modifier = Modifier.requiredSize(24.dp),
                                    colorFilter = ColorFilter.tint(Color.Black),
                                    contentDescription = "Search icon"
                                )
                            }
                        )
                    },
                    colors = SearchBarDefaults.colors(containerColor = Color(0xFFACACAC)),
                    expanded = false,
                    onExpandedChange = { expanded = it },
                    content = {},
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 20.dp)
                )
            }
        }

        item {
            SectionHeader(title = "Actors and Genres")
        }

        when (nonMovieBoxItemsUIState) {
            NonMovieBoxItemsUIState.Empty -> {
                item {
                    Text(
                        text = "No movies to be found",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = SearchColorForText
                    )
                }
            }
            is NonMovieBoxItemsUIState.Data -> {
                var putDivider = false;
                itemsIndexed(nonMovieBoxItemsUIState.nonMovieBoxes) { index, nonMovieBoxItem ->
                    if (index > 0) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                            ) {
                            HorizontalDivider(
                                color = SearchColorForText,
                                thickness = 1.dp,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth(0.7f)
                            )
                        }
                    }
                    NonMovieBoxRow(nonMovieBox = nonMovieBoxItem)
                }
            }
        }
        item {
            SectionHeader(title = "Movies and Series")
        }

        when (movieItemsUIState) {
            MovieItemsUIState.Empty -> {
                item {
                    Text(
                        text = "No movies to be found",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = SearchColorForText
                    )
                }
            }
            is MovieItemsUIState.Data -> {
                itemsIndexed(movieItemsUIState.movies.results) { index, movieBoxItem ->
                    if (index > 0) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            HorizontalDivider(
                                color = SearchColorForText,
                                thickness = 1.dp,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth(0.7f)
                            )
                        }
                    }
                    MovieBoxRow(
                        movie = movieBoxItem,
                        modifier = Modifier.clickable(
                            onClick = { onNavigateToMedia(movieBoxItem.title) }
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
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
            text = title,
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
}
