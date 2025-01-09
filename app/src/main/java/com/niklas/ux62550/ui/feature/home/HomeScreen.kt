package com.niklas.ux62550.ui.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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

    UX62550Theme(darkTheme = true) {
        HomeScreen(onNavigateToMedia = {}, homeViewModel = mvm)
        // TODO: perhaps split up the feature items and genre items into composables so we can preview them individually
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(),
    onNavigateToMedia: (MediaObject) -> Unit
) {
    val mediaItemsUIState: MediaItemsUIState = homeViewModel.mediaItemsState.collectAsState().value
    val movieGenresDataState: GenresDataState = homeViewModel.movieGenresState.collectAsState().value
    when (mediaItemsUIState) {
        MediaItemsUIState.Empty -> {
            LoadingScreen()
        }

        is MediaItemsUIState.Data -> {
            Column(modifier.verticalScroll(rememberScrollState())) {
                HomeWelcomeTopBar()
                FeaturedMediaScroller(mediaItemsUIState.mediaObjects, onNavigateToMedia)
                GenreMediaStack(movieGenresDataState, onNavigateToMedia)
            }
        }
    }
}

@Composable
fun HomeWelcomeTopBar(modifier: Modifier = Modifier) {
    Row(
        modifier.fillMaxWidth().padding(30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        LogoBox(size = 55.dp)
        Spacer(Modifier.width(20.dp))
        Text(
            text = "Welcome, User",
            style = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight.Normal
            )
        )
    }
}

@Composable
fun FeaturedMediaScroller(featuredMedia: List<MediaObject>, onNavigateToMedia: (MediaObject) -> Unit) {
    HomeFeaturedMediaHorizontalPager(featuredMedia.subList(0, 7), onNavigateToMedia)
}

@Composable
fun GenreMediaStack(movieGenresDataState: GenresDataState, onNavigateToMedia: (MediaObject) -> Unit) {
    when (movieGenresDataState) {
        GenresDataState.Empty -> {
            // TODO: We should make a loading screen for all the genres, so that they are only displayed when ALL genre fetching is done. Async image loading may be neglected from said fetching
        }

        is GenresDataState.Data -> {
            for (genre in movieGenresDataState.genres) {
                DiscoverSlider(
                    discoverViewModel = viewModel(factory = DiscoverViewModelFactory(genre), key = genre.id.toString()),
                    headerTitle = genre.name,
                    onNavigateToMedia = onNavigateToMedia
                )
            }
            Spacer(Modifier.height(20.dp))
        }
    }
}
