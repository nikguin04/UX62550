package com.niklas.ux62550.ui.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import com.niklas.ux62550.models.figmaPxToDp_h
import com.niklas.ux62550.ui.feature.common.LogoBox
import com.niklas.ux62550.ui.theme.UX62550Theme

@Composable
@Preview(showBackground = true)
fun HomePreview() {
    val mvm: MediaItemsViewModel = viewModel()
    mvm.initPreview()

    UX62550Theme(darkTheme = true) {
        HomeScreen(onNavigateToMedia = {})
    }
}

@Composable
fun HomeScreen(
    onNavigateToMedia: (String) -> Unit,
    mediaItemsViewModel: MediaItemsViewModel = viewModel()
) {
    val uiState = mediaItemsViewModel.mediaItemsState.collectAsState().value
    ScreenHome(mediaItemsUIState = uiState, onNavigateToMedia = onNavigateToMedia)
}

@Composable
fun ScreenHome(modifier: Modifier = Modifier, mediaItemsUIState: MediaItemsUIState, onNavigateToMedia: (String) -> Unit) {
    Column(modifier.padding()) {
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

        // Implement viewmodel
        when (mediaItemsUIState) {
            MediaItemsUIState.Empty -> {
                Text(
                    text = "No Media Items",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            is MediaItemsUIState.Data -> {
                HomeFeaturedMediaHorizontalPager(mediaItemsUIState.mediaObjects, onNavigateToMedia)

                HorizontalDotIndexer(Modifier.size(LocalConfiguration.current.screenWidthDp.dp, 12.dp))
            }
            else -> {}
        }

        val genres = listOf("Romance", "Action", "Comedy", "Drama")

        for (genre in genres) {
            // TODO: PLEASE READ! this is a placeholder for when we get a proper structure for fetching movies
            /*when (mediaItemsUIState) {
                MediaItemsUIState.Empty -> {*/
                    Text(
                        text = "No Media Items",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                /*}
                is MediaItemsUIState.Data -> {
                    Row(
                        Modifier.padding(
                            15.dp,
                            12.dp,
                            0.dp,
                            2.dp
                        )
                    ) {
                        Text(text = genre)
                    }
                    HorizontalLazyRowMovies(
                        Modifier.padding(0.dp, 0.dp),
                        155f,
                        87.19f,
                        mediaItemsUIState.mediaItems,
                        onNavigateToMedia
                    )
                }
                else -> {}
            }*/
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
