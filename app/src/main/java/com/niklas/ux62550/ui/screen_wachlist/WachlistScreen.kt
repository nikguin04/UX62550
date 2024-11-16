package com.niklas.ux62550.ui.screen_wachlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.niklas.ux62550.models.figmaPxToDp_h
import com.niklas.ux62550.models.figmaPxToDp_w
import com.niklas.ux62550.ui.screen_home.LogoBox
import com.niklas.ux62550.ui.screen_search.MovieBoxRow
import com.niklas.ux62550.ui.screen_search.MovieItemsUIState
import com.niklas.ux62550.ui.theme.SearchColorForText
import com.niklas.ux62550.ui.theme.UX62550Theme

@Composable
@Preview(showBackground = true)
fun WatchlistPreview() {
    UX62550Theme(darkTheme = true, dynamicColor = false) {
        Surface(modifier = Modifier.fillMaxSize()) {
            WatchlistScreen(onNavigateToMedia = {})
        }
    }
}

@Composable
fun WatchlistScreen(viewModel: WatchlistViewModel = viewModel(), onNavigateToMedia: (String) -> Unit) {
    val movies = viewModel.moviesState.collectAsState().value
    WatchlistContent(movieItemsUIState = movies, onNavigateToMedia = onNavigateToMedia)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchlistContent(modifier: Modifier = Modifier, movieItemsUIState: MovieItemsUIState, onNavigateToMedia: (String) -> Unit) {
    Column(modifier.padding()) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(figmaPxToDp_w(0f), figmaPxToDp_h(40f), 0.dp, figmaPxToDp_h(17.5f)), // ConvertPxToDp(29.5f)
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBar(
                inputField = {
                    var text = ""
                    var expanded = false

                    SearchBarDefaults.InputField(
                        query = text,
                        onQueryChange = { text = it },
                        onSearch = { expanded = false },
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        placeholder = {
                            Text(text = "Search in Favorites", color = Color(0xFF707070), fontSize = 12.sp, overflow = TextOverflow.Visible)
                        },
                        leadingIcon = {
                            Image( // Needs to be made button
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
            LogoBox(size = figmaPxToDp_w(180f))
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(figmaPxToDp_w(0f), figmaPxToDp_h(20f), 0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = "Watch List", fontSize = 36.sp)
                Button(
                    onClick = { /* Do something! */ },
                    shape = RoundedCornerShape(7.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFFACACAC)),
                    contentPadding = PaddingValues(horizontal = 5.dp, vertical = 0.dp),
                    modifier = modifier
                        .height(25.dp)
                        .width(90.dp)
                ) {
                    Text("Sort by")
                    Spacer(modifier = Modifier.weight(0.1f))
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Dropdown Icon",
                    )
                }
            }
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
                    items(movieItemsUIState.movies) { movieItem ->
                        HorizontalDivider(
                            color = SearchColorForText,
                            thickness = 1.dp,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(0.7f)
                        )
                        MovieBoxRow(
                            movie = movieItem,
                            modifier = Modifier.clickable(
                                onClick = { onNavigateToMedia(movieItem.name) }
                            )
                        )
                    }
                }
            }
        }
    }
}
