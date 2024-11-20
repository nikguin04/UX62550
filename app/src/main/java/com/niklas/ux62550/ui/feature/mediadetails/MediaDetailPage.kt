package com.niklas.ux62550.ui.feature.mediadetails

import ActorsAndDirectorsPopUp
import AwardPopUp
import DrawCircle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.StarHalf
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.PlayCircleOutline
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.niklas.ux62550.models.MediaItem
import com.niklas.ux62550.models.Movie
import com.niklas.ux62550.ui.feature.home.HorizontalLazyRowWithSnapEffect
import com.niklas.ux62550.ui.feature.home.MediaItemsUIState
import com.niklas.ux62550.ui.feature.home.MediaItemsViewModel
import com.niklas.ux62550.ui.feature.popup.DetailRatingPopUp
import com.niklas.ux62550.ui.theme.UX62550Theme
import kotlin.time.Duration

@Composable
@Preview(showBackground = true)
fun MediaDetailPagePreview() {
    UX62550Theme(darkTheme = true, dynamicColor = false) {
        Surface {
            MediaDetailsScreen(onNavigateToOtherMedia = {}, onNavigateToReview = {})
        }
    }
}

@Composable
fun MediaDetailsScreen(
    viewModel: MovieViewModel = viewModel(),
    onNavigateToOtherMedia: (String) -> Unit,
    onNavigateToReview: (String) -> Unit,
    mediaItemsViewModel: MediaItemsViewModel = viewModel()
){
    val movie = viewModel.movieState.collectAsState().value
    val similarMedia = viewModel.similarMediaState.collectAsState().value
    val uiState = mediaItemsViewModel.mediaItemsState.collectAsState().value
    MediaDetailsContent(
        movie = movie,
        similarMedia = similarMedia,
        mediaItemsUIState = uiState,
        onNavigateToOtherMedia = onNavigateToOtherMedia,
        onNavigateToReview = onNavigateToReview)
}


@Composable
fun MediaDetailsContent(
    modifier: Modifier = Modifier,
    movie: Movie,
    similarMedia: List<MediaItem>,
    mediaItemsUIState: MediaItemsUIState,
    onNavigateToOtherMedia: (String) -> Unit,
    onNavigateToReview: (String) -> Unit
){
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Box(modifier = modifier.fillMaxWidth()) {
            Box(modifier = Modifier.alpha(0.5f)) {
                Box(
                    modifier = Modifier
                        .background(Color.Red)
                        .fillMaxWidth()
                        .height(230.dp)
                )
            }
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(30.dp)
                    .absolutePadding(0.dp, 40.dp, 4.dp, 0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(movie.tempColor)
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                )
                Image(
                    Icons.Outlined.PlayCircleOutline,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .absolutePadding(0.dp, 0.dp, 0.dp, 40.dp)
                        .requiredSize(72.dp),
                    colorFilter = ColorFilter.tint(Color.White),
                    contentDescription = "Play circle"
                )
                // Implement viewmodel
                when (mediaItemsUIState) {
                    MediaItemsUIState.Empty -> {
                        TitleText("No movie yet")
                    }
                    is MediaItemsUIState.Data -> {
                        MovieDetailsAPI(mediaItemsUIState.mediaObjects)
                    }
                    else -> {}
                }
            }

            Image(
                Icons.Outlined.BookmarkBorder,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(20.dp),
                colorFilter = ColorFilter.tint(Color.White),
                contentDescription = "Bookmark"
            )

            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart) // Align to bottom start within the Box
                    .padding(4.dp, 0.dp, 8.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                facts(modifier, movie.name, movie.rating, movie.year, movie.duration, movie.pgRating, onNavigateToReview)
            }
        }
        Genre(movie.genres)
        DescriptionText(movie.description)
        Text("Actors and Directors", modifier.padding(4.dp, 2.dp, 0.dp, 0.dp))
        ActorsAndDirectorsPopUp()
        AwardPopUp()
        DetailRatingPopUp()


        Text("Movies similar to this one", modifier.padding(4.dp, 0.dp, 0.dp, 0.dp))
        HorizontalLazyRowWithSnapEffect(similarMedia, onNavigateToOtherMedia)
    }
}
@Composable
fun facts(
    modifier: Modifier,
    movieName: String,
    movieRating: Double,
    MovieYear: String,
    MovieDuration: Duration,
    MoviePGRating: Int,
    onNavigateToReview: (String) -> Unit
) {
    Row {
        Row(
            modifier = Modifier.clickable(onClick = { onNavigateToReview(movieName) }),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 1..5) {
                val starIcon = when {
                    i <= movieRating -> Icons.Outlined.Star
                    i <= movieRating + 0.5 -> Icons.AutoMirrored.Outlined.StarHalf
                    else -> Icons.Outlined.StarOutline
                }
                Image(
                    imageVector = starIcon,
                    modifier = Modifier.requiredSize(18.dp),
                    colorFilter = ColorFilter.tint(Color.Yellow),
                    contentDescription = "Rating star"
                )
            }
            Spacer(modifier = modifier.width(4.dp))
            Text(
                movieRating.toString(),
                fontSize = 18.sp
            )
        }
        Spacer(modifier = modifier.weight(1f))
        Text(
            MovieYear,
            fontSize = 18.sp
        )
        Spacer(modifier = modifier.weight(1f))
        Text(
            MovieDuration.toString(),
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            "$MoviePGRating+",
            fontSize = 18.sp
        )
    }
}

@Composable
fun Genre(movieGenre: List<String>){
    Row(
        modifier = Modifier
            .padding(4.dp, 10.dp, 0.dp, 0.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for ((index, genre) in movieGenre.withIndex()) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(40.dp))
                    .background(Color.Gray)
                    .padding(8.dp, 4.dp)
            ) {
                Text(
                    text = genre,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            if (index != movieGenre.lastIndex) {
                Spacer(modifier = Modifier.width(4.dp))
                DrawCircle(Modifier.size(10.dp), Color.LightGray)
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
        Spacer(modifier = Modifier.weight(0.3f))
        repeat(3) { // Needs to be changed to Where to Watch
            Spacer(modifier = Modifier.width(4.dp))
            DrawCircle(Modifier.size(10.dp), Color.LightGray)
        }
        Spacer(modifier = Modifier.weight(0.05f))
    }
}