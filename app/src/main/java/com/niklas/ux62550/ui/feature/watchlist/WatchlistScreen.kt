package com.niklas.ux62550.ui.feature.watchlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.ui.feature.common.LogoBox
import com.niklas.ux62550.ui.feature.common.composables.MovieBoxRowFromId
import com.niklas.ux62550.ui.theme.SearchColorForText
import com.niklas.ux62550.ui.theme.UX62550Theme

@Composable
@Preview(showBackground = true)
fun WatchlistPreview() {
    UX62550Theme {
        Surface(modifier = Modifier.fillMaxSize()) {
//            WatchlistScreen(onNavigateToMedia = {})
        }
    }
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchlistContent(modifier: Modifier = Modifier, onNavigateToMedia: (MediaObject) -> Unit, watchlistViewModel: WatchlistViewModel = viewModel()) {
    Column(Modifier.padding()) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 45.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val watchListState = watchlistViewModel.watchListState.collectAsState().value
            val watchListRowState = watchlistViewModel.watchListRowState.collectAsState().value

            var text by rememberSaveable { mutableStateOf("") }
            var expanded by rememberSaveable { mutableStateOf(false) }

            LogoBox(size = 200.dp)
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top= 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = "Watch List", fontSize = 36.sp)
            }
            when (watchListState) {
                MovieIds.Empty -> {
                    Text("No data yet")
                }

                is MovieIds.Data -> {
                    // Display list of movie items in LazyColumn
                    val movieIDList: List<Int> = watchListState.movies?:emptyList()
                    Column {
                        movieIDList.forEachIndexed { index, id ->
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
                            MovieBoxRowFromId(Modifier.padding(16.dp), id, onNavigateToMedia = onNavigateToMedia, Modifier.padding(start = 16.dp))
                        }
                    }
                }
                is MovieIds.Error -> {
                    Text("Network Error")
                }
            }
        }
    }
}
