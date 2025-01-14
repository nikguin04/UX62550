package com.niklas.ux62550.ui.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.ui.feature.common.DiscoverViewModelFactory
import com.niklas.ux62550.ui.feature.common.LogoBox
import com.niklas.ux62550.ui.feature.loadingscreen.LoadingScreen
import com.niklas.ux62550.ui.theme.UX62550Theme

@Composable
@Preview(showBackground = true)
fun HomePreview() {
    val mvm: HomeViewModel = viewModel()
    mvm.initPreview()

    UX62550Theme {
        HomeScreen(onNavigateToMedia = {}, homeViewModel = mvm)
        // TODO: perhaps split up the feature items and genre items into composables so we can preview them individually
    }
}


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(),
    onNavigateToMedia: (MediaObject) -> Unit) {
    val mediaItemsUIState: MediaItemsUIState = homeViewModel.mediaItemsState.collectAsState().value
    when (mediaItemsUIState) {
        MediaItemsUIState.Empty -> {
            LoadingScreen()
        }
        is MediaItemsUIState.Data -> {

            Column(modifier.padding().verticalScroll(rememberScrollState())) {
                FeaturedMediaScroller(homeViewModel, onNavigateToMedia)
                GenreMediaStack(homeViewModel, onNavigateToMedia)
            }
        }
    }
}

@Composable
fun HomeWelcomeTopBar(modifier: Modifier = Modifier) {
    Row(
        modifier.fillMaxWidth().padding(32.dp, 45.dp, 0.dp, 38.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        LogoBox(size = 55.dp)

        Box(modifier = Modifier.padding(20.dp, 0.dp, 0.dp, 0.dp)) {
            Text(
                text = "Welcome, User",
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }
}

@Composable
fun FeaturedMediaScroller(homeViewModel: HomeViewModel, onNavigateToMedia: (MediaObject) -> Unit) {
    val mediaItemsUIState: MediaItemsUIState = homeViewModel.mediaItemsState.collectAsState().value
    when (mediaItemsUIState) {
        MediaItemsUIState.Empty -> {
            LoadingScreen()
        }

        is MediaItemsUIState.Data -> {
        	HomeWelcomeTopBar()
            HomeFeaturedMediaHorizontalPager(mediaItemsUIState.mediaObjects.subList(0,7), onNavigateToMedia)
        }
        else -> {}
    }
}

@Composable
fun GenreMediaStack(homeViewModel: HomeViewModel, onNavigateToMedia: (MediaObject) -> Unit) {
    val movieGenresDataState: GenresDataState = homeViewModel.movieGenresState.collectAsState().value
    when (movieGenresDataState) {
        GenresDataState.Empty -> {

            // No placeholder here since it is managed by the individual genre viewmodels
            // TODO: We should make a loading screen for all the genres, so that they are only displayed when ALL genre fetching is done. Async image loading may be neglected from said fetching
        }

        is GenresDataState.Data -> {
            for (genreObject in movieGenresDataState.genres) {
                DiscoverSlider (
                    discoverViewModel = viewModel(factory = DiscoverViewModelFactory(genreObject), key = genreObject.id.toString()),
                    headerTitle = genreObject.name,
                    onNavigateToMedia = onNavigateToMedia
                )
            }
            Box(Modifier.size(0.dp, 20.dp))
        }
    }
}
