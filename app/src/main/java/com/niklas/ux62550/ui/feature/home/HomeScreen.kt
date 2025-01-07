package com.niklas.ux62550.ui.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.niklas.ux62550.data.model.GenreObject
import com.niklas.ux62550.data.model.KeywordObject
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.ui.feature.common.DiscoverViewModel
import com.niklas.ux62550.ui.feature.common.DiscoverViewModelFactory
import com.niklas.ux62550.ui.feature.common.LogoBox
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
fun HomeScreen(modifier: Modifier = Modifier, homeViewModel: HomeViewModel = viewModel(), onNavigateToMedia: (String) -> Unit) {
    Column(modifier.padding().verticalScroll(rememberScrollState())) {
        Row(
            modifier.fillMaxWidth().padding(32.dp, 45.dp, 0.dp, 38.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            LogoBox(size = 55.dp)

            Box(modifier = Modifier.padding(20.dp, 0.dp, 0.dp, 0.dp)) {
                Text(
                    text = "Welcome, User1",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }

        // Featured items
        val mediaItemsUIState: MediaItemsUIState = homeViewModel.mediaItemsState.collectAsState().value
        when (mediaItemsUIState) {
            MediaItemsUIState.Empty -> {
                /*Text(
                    text = "No Media Items",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )*/
                // TODO: CWL will make a proper loading page so this is disregarded for now
            }

            is MediaItemsUIState.Data -> {
                HomeFeaturedMediaHorizontalPager(mediaItemsUIState.mediaObjects, onNavigateToMedia)
                Box(Modifier.size(4.dp))
                HorizontalDotIndexer(Modifier.size(LocalConfiguration.current.screenWidthDp.dp, 12.dp))
            }
            else -> {}
        }

        // Genre items
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
}

@Composable
fun HorizontalDotIndexer(modifier: Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0..4) {
            Icon(
                Icons.Filled.Circle,
                contentDescription = "test123",
                modifier = Modifier
                    .padding(1.5.dp, 0.dp, 1.5.dp, 0.dp)
                    .size(12.dp)
                    .shadow(
                        elevation = 4.dp,
                        shape = CircleShape,
                        ambientColor = Color.Black.copy(alpha = 255f), // Slightly less opaque for a softer effect
                        spotColor = Color.Black.copy(alpha = 255f)
                    )
            )
        }
    }
}
