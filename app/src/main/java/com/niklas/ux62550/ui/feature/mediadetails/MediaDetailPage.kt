package com.niklas.ux62550.ui.feature.mediadetails

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.StarHalf
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.PlayCircleOutline
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.niklas.ux62550.data.examples.SearchDataExamples
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.data.model.MovieDetailObject
import com.niklas.ux62550.navigation.GeneralTopBar
import com.niklas.ux62550.ui.feature.common.CreditState
import com.niklas.ux62550.ui.feature.common.CreditViewModel
import com.niklas.ux62550.ui.feature.common.CreditsViewModelFactory
import com.niklas.ux62550.ui.feature.common.ImageSize
import com.niklas.ux62550.ui.feature.common.MediaItem
import com.niklas.ux62550.ui.feature.common.MediaItemBackdropIntercept
import com.niklas.ux62550.ui.feature.common.ShadowIcon
import com.niklas.ux62550.ui.feature.loadingscreen.LoadingScreen
import com.niklas.ux62550.ui.feature.watchlist.MovieIds
import com.niklas.ux62550.ui.feature.watchlist.WatchlistViewModel
import com.niklas.ux62550.ui.theme.UX62550Theme
import java.util.Locale
import kotlin.time.Duration.Companion.minutes

@Composable
@Preview(showBackground = true)
fun MediaDetailPagePreview() {
    val media = SearchDataExamples.MediaObjectExample
    val movieViewModel: MovieViewModel = viewModel(factory = MovieViewModelFactory(media = media))
    movieViewModel.initPreview()

    val creditsViewModel: CreditViewModel = viewModel(factory = CreditsViewModelFactory(media = media))
    creditsViewModel.initPreview()

    UX62550Theme {
        Surface {
            MediaDetailsScreen(movieViewModel = movieViewModel, creditsViewModel = creditsViewModel, navigateBack = {}, onNavigateToOtherMedia = {}, onNavigateToReview = {})
        }
    }
}

@Composable
fun MediaDetailsScreen(
    movieViewModel: MovieViewModel,
    creditsViewModel: CreditViewModel,
    navigateBack: () -> Unit,
    onNavigateToOtherMedia: (MediaObject) -> Unit,
    onNavigateToReview: (MovieDetailObject) -> Unit,
    modifier: Modifier = Modifier,
    watchlistViewModel: WatchlistViewModel = viewModel()
) {
    val movieState = movieViewModel.movieState.collectAsState().value
    val similarMediaState = movieViewModel.similarMediaState.collectAsState().value
    val trailerState = movieViewModel.trailerState.collectAsState().value
    val creditState = creditsViewModel.creditState.collectAsState().value
    val providerState = movieViewModel.providerState.collectAsState().value
    val watchListState = watchlistViewModel.watchListState.collectAsState().value

    when {
        movieState is MovieState.Empty || creditState is CreditState.Empty -> {
            Box {
                LoadingScreen()
                GeneralTopBar(navigateBack = navigateBack)
            }
        }

        movieState is MovieState.Data && creditState is CreditState.Data -> {
            Box {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    Header(movieState = movieState, trailerState = trailerState)
                    Column(modifier = modifier) {
                        InfoRow(movieState = movieState)
                        Genres(genres = movieState, providerState = providerState)
                        DescriptionText(description = movieState.mediaDetailObject.Description)

                        ActorsAndDirectors(creditState = creditState)
                        DetailedRating(movieViewModel = movieViewModel, movieID = movieState, onNavigateToReview = onNavigateToReview)
                        SimilarMedia(modifier = Modifier.padding(vertical = 20.dp), similarMediaState = similarMediaState, onNavigateToOtherMedia = onNavigateToOtherMedia)
                    }

                }
                    GeneralTopBar(navigateBack = navigateBack) {
                    BookmarkButton(movieState.mediaDetailObject.toMediaObject(), movieViewModel, watchListState)
                }
            }
        }
    }
}

@Composable
fun Header(modifier: Modifier = Modifier, movieState: MovieState.Data, trailerState: TrailerState) {
    val context = LocalContext.current
    Box(modifier = modifier.fillMaxWidth()) {
        // Background Image with Transparency
        Box(modifier = Modifier.alpha(0.5f)) {
            MediaItem(
                uri = movieState.mediaDetailObject.backDropPath,
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                    .drawWithContent {
                        drawContent() // Draw the actual image
                        drawRect( // Draw the fade
                            brush = Brush.verticalGradient(
                                listOf(Color.Black, Color.Transparent)
                            ),
                            blendMode = BlendMode.DstIn
                        )
                    },
                size = ImageSize.BACKDROP
            )
        }
        when (trailerState) {
            TrailerState.Empty -> {}
            is TrailerState.Data -> {
                var youtubeUrl = trailerState.trailerObject.resultsTrailerLinks.find { it.type == "Trailer" }?.let {
                    "https://www.youtube.com/watch?v=${it.key}"
                }
                if (youtubeUrl == null && trailerState.trailerObject.resultsTrailerLinks.isNotEmpty()) {
                    youtubeUrl = "https://www.youtube.com/watch?v=${trailerState.trailerObject.resultsTrailerLinks[0].key}"
                }

                Column(
                    modifier = Modifier
                        .padding(30.dp, 70.dp, 30.dp, 8.dp)
                        .fillMaxWidth()
                ) {
                    val playModifier =
                        if (youtubeUrl == null) { Modifier }
                        else {
                            Modifier.clickable {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl)).apply {
                                    setPackage("com.google.android.youtube")
                                }
                                if (intent.resolveActivity(context.packageManager) != null) {
                                    context.startActivity(intent)
                                } else {
                                    // Fallback to a web browser
                                    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
                                    context.startActivity(webIntent)
                                }

                            }
                        }
                    Box( // Playable Trailer Box
                        modifier = playModifier
                            .aspectRatio(16f / 9f)
                            .fillMaxWidth()
                    ) {
                        MediaItemBackdropIntercept(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 9f)
                                .clip(RoundedCornerShape(6.dp)),
                            fetchEnBackdrop = true,
                            backdropFallback = false,
                            mediaItem = movieState.mediaDetailObject.toMediaObject()
                        )
                        if (youtubeUrl != null) {
                            Image(
                                Icons.Outlined.PlayCircleOutline,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .requiredSize(72.dp),
                                colorFilter = ColorFilter.tint(Color.White),
                                contentDescription = "Play circle"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    TitleText(movieState.mediaDetailObject.title)
                }
            }
            is TrailerState.Error -> {
                Text(text = "Network error")
            }
        }
    }
}

@Composable
fun InfoRow(modifier: Modifier = Modifier, movieState: MovieState.Data) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Row for the stars so that we can use spaced evenly
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            val rating = movieState.mediaDetailObject.vote_average / 2
            for (i in 1..5) {
                val starIcon = when {
                    i <= rating -> Icons.Outlined.Star
                    i <= rating + 0.5 -> Icons.AutoMirrored.Outlined.StarHalf
                    else -> Icons.Outlined.StarOutline
                }
                Image(
                    imageVector = starIcon,
                    modifier = Modifier
                        .requiredSize(18.dp)
                        .shadow(
                            elevation = 15.dp,
                            ambientColor = Color.Black.copy(alpha = 255f), // Slightly less opaque for a softer effect
                        ),
                    colorFilter = ColorFilter.tint(Color.Yellow),
                    contentDescription = "Rating star"
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                String.format(Locale.ENGLISH, "%.1f", rating),
                fontSize = 18.sp
            )
        }
        Text(
            text = if(movieState.mediaDetailObject.releaseDate.count() > 4){ movieState.mediaDetailObject.releaseDate.substring(0, 4) } else "N/A",
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.White,
                shadow = Shadow(color = Color.Black, blurRadius = 5f)
            )
        )
        Text(
            movieState.mediaDetailObject.runTime.minutes.toString(),
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.White,
                shadow = Shadow(color = Color.Black, blurRadius = 5f)
            )
        )
    }
}

@Composable
fun TitleText(title: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = title,
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            shadow = Shadow(
                color = Color.Black, blurRadius = 10f
            ),
            textAlign = TextAlign.Center
        )
    )
}

@Composable
fun DescriptionText(description: String) {
    Box(
        modifier = Modifier
            .padding(20.dp, 10.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(color = Color(0xFF353433))
    ) {
        Text(
            text = description,
            style = TextStyle(
                lineHeight = 1.25.em,
                lineBreak = LineBreak.Paragraph,
                hyphens = Hyphens.Auto,
                fontSize = 18.sp,
                textAlign = TextAlign.Justify,
                color = Color.White,
                shadow = Shadow(color = Color.Black, blurRadius = 7.5f)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )
    }
}

@Composable
fun BookmarkButton(media: MediaObject, movieViewModel: MovieViewModel, watchlistState: MovieIds) {
    var isBookmarked by remember { mutableStateOf(false) }

    if (watchlistState is MovieIds.Data) {
        isBookmarked = watchlistState.movies?.contains(media.id) ?: false
    }

    IconButton(onClick = {
        movieViewModel.updateToDatabase(media, isBookmarked)
        isBookmarked = !isBookmarked
    }) {
        ShadowIcon(
            imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
            contentDescription = "Bookmark"
        )
    }
}
