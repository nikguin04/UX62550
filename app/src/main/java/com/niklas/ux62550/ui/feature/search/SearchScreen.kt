package com.niklas.ux62550.ui.feature.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.widthIn
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
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.ui.feature.common.composables.MovieBoxRow
import com.niklas.ux62550.ui.feature.common.composables.NonMovieBoxRow
import com.niklas.ux62550.ui.theme.SearchColorForText
import com.niklas.ux62550.ui.theme.UX62550Theme

@Composable
fun SearchScreen(onNavigateToMedia: (MediaObject) -> Unit, topModifier: Modifier = Modifier, viewModel: SearchViewModel = viewModel()) {
    val moviesState = viewModel.movieItemsUIState.collectAsState().value
    SearchContent(topModifier = topModifier, viewModel = viewModel, movieItemsUIState = moviesState, onNavigateToMedia = onNavigateToMedia)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchContent(
    viewModel: SearchViewModel,
    movieItemsUIState: MovieItemsUIState,
    onNavigateToMedia: (MediaObject) -> Unit,
    topModifier: Modifier = Modifier
) {
    var text by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    LazyColumn {
        item { Spacer(modifier = topModifier) }
        item {
            Row(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // We used the example to get started on the SearchBar
                // androidx.compose.material3.samples.SearchBarSample
                SearchBar(
                    inputField = {
                        SearchBarDefaults.InputField(
                            query = text,
                            onQueryChange = { text = it; viewModel.onSearchQueryChanged(it) },
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
                            },
                            modifier = Modifier
                        )
                    },

                    colors = SearchBarDefaults.colors(containerColor = Color(0xFFACACAC)),
                    expanded = false,
                    onExpandedChange = { expanded = it },
                    content = {},
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .widthIn(max = 360.dp)
                )
            }
        }

        when (movieItemsUIState) {
            is MovieItemsUIState.Empty -> {
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "No movies found",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = SearchColorForText
                        )
                    }
                }
            }
            is MovieItemsUIState.Error -> {
                item { Text("Network error") }
            }
            is MovieItemsUIState.Data -> {
                val movieList: List<MediaObject> = movieItemsUIState.movies.results.filter { it.mediaType == "movie" }
                val actorList: List<MediaObject> = movieItemsUIState.movies.results.filter { it.mediaType == "person" }

                item {
                    SectionHeader(title = "Movies")
                }
                itemsIndexed(movieList) { index, movieBoxItem ->
                    if (index != 0) {
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
                        modifier = Modifier
                            .clickable { onNavigateToMedia(movieBoxItem) }
                            .padding(16.dp),
                        infoRowModifier = Modifier.padding(start = 16.dp)
                    )
                }

                item {
                    SectionHeader(title = "Actors")
                }
                itemsIndexed(actorList) { index, movieBoxItem ->
                    if (index != 0) {
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
                    NonMovieBoxRow(person = movieBoxItem)
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

@Composable
@Preview(showBackground = true)
fun SearchPreview() {
    UX62550Theme {
        val viewModel: SearchViewModel = viewModel()
        viewModel.initPreview()

        Surface(modifier = Modifier.fillMaxSize()) {
            SearchScreen(viewModel = viewModel, onNavigateToMedia = {})
        }
    }
}
