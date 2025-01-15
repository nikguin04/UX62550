
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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.niklas.ux62550.data.examples.SearchDataExamples
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.data.model.MovieDetailObject
import com.niklas.ux62550.data.remote.FirebaseInstance
import com.niklas.ux62550.data.remote.RemoteFirebase
import com.niklas.ux62550.domain.WatchListRepository
import com.niklas.ux62550.ui.feature.common.CreditState
import com.niklas.ux62550.ui.feature.common.CreditViewModel
import com.niklas.ux62550.ui.feature.common.CreditsViewModelFactory
import com.niklas.ux62550.ui.feature.common.ImageSize
import com.niklas.ux62550.ui.feature.common.MediaItem
import com.niklas.ux62550.ui.feature.common.MediaItemBackdropIntercept
import com.niklas.ux62550.ui.feature.loadingscreen.LoadingScreen
import com.niklas.ux62550.ui.theme.DescriptionColor
import com.niklas.ux62550.ui.feature.watchlist.WatchlistContent
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

    UX62550Theme {
        Surface {
            MediaDetailsScreen(movieViewModel, creditsViewModel, onNavigateToOtherMedia = {}, onNavigateToReview = {})
        }
    }
}

@Composable
fun MediaDetailsScreen(
    movieViewModel: MovieViewModel,
    creditsViewModel: CreditViewModel,
    onNavigateToOtherMedia: (MediaObject) -> Unit,
    onNavigateToReview: (MovieDetailObject) -> Unit
) {

    val movieState = movieViewModel.movieState.collectAsState().value
    val similarMediaState = movieViewModel.similarMediaState.collectAsState().value
    val trailerState = movieViewModel.trailerState.collectAsState().value
    val creditState = creditsViewModel.creditState.collectAsState().value
    val providerState = movieViewModel.providerState.collectAsState().value

    when  {
       movieState is MovieState.Empty || creditState is CreditState.Empty -> {
            LoadingScreen()
        }

       movieState is MovieState.Data && creditState is CreditState.Data-> {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            )
            {
                Header(movieState = movieState, trailerState = trailerState, movieViewModel = movieViewModel)
                InfoRow(movieState = movieState, onNavigateToReview = onNavigateToReview)
                Genres(genres = movieState, providerState = providerState)
                DescriptionText(description = movieState.mediaDetailObject.Description)

                ActorsAndDirectors(creditState = creditState)
                DetailedRating()
                SimilarMedia(similarMediaState = similarMediaState, onNavigateToOtherMedia = onNavigateToOtherMedia)


            }
        }
    }
}
@Composable
fun Header(modifier: Modifier = Modifier, movieState: MovieState.Data, trailerState: TrailerState, movieViewModel: MovieViewModel) {
    val context = LocalContext.current
    Box(modifier = modifier.fillMaxWidth()) {
        // Background Image with Transparency
        Box(modifier = Modifier.alpha(0.5f)) {
            val backColor = MaterialTheme.colorScheme.background
            MediaItem(
                uri = movieState.mediaDetailObject.backDropPath,
                modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer(alpha = 1f)
                .drawWithContent {
                    drawContent() // Draw the actual image

                    // Draw the fade
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                backColor,
                                backColor.copy(alpha = 0.5f),
                                Color.Transparent,
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY // Adjust for gradient depth
                        ),
                        blendMode = androidx.compose.ui.graphics.BlendMode.DstIn
                    )
                },
                size = ImageSize.BACKDROP


            )
        }
    when (trailerState) {
        TrailerState.Empty -> {

        }
        is TrailerState.Data -> {
            var youtubeUrl = trailerState.trailerObject.resultsTrailerLinks.find { it.type == "Trailer" }?.let {
                "https://www.youtube.com/watch?v=${it.key}"
            }
            if(youtubeUrl == null && trailerState.trailerObject.resultsTrailerLinks.isNotEmpty()){
                youtubeUrl = "https://www.youtube.com/watch?v=${trailerState.trailerObject.resultsTrailerLinks[0].key}"
            }
            Column(
                modifier = Modifier
                    .padding(30.dp, 70.dp, 30.dp, 8.dp)
                    .fillMaxWidth()
            ) {
                Box( // Playable Trailer Box
                    modifier = Modifier
                        .aspectRatio(16f / 9f)
                        .fillMaxWidth()
                        .clickable {
                            youtubeUrl?.let {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it)).apply {
                                    setPackage("com.google.android.youtube")
                                }
                                if (intent.resolveActivity(context.packageManager) != null) {
                                    context.startActivity(intent)
                                } else {
                                    // Fallback to a web browser
                                    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                                    context.startActivity(webIntent)
                                }
                            }
                        }
                ) {
                    MediaItemBackdropIntercept(
                        //uri = movieState.mediaDetailObjects.backDropPath,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f),
                        fetchEnBackdrop = true,
                        mediaItem = movieState.mediaDetailObject.toMediaObject()
                    )
                    Image(
                        Icons.Outlined.PlayCircleOutline,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .requiredSize(72.dp),
                        colorFilter = ColorFilter.tint(Color.White),
                        contentDescription = "Play circle"
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                TitleText(movieState.mediaDetailObject.Originaltitle)
            }
        }
         else -> {}
    }

        // Bookmark Button
        BookmarkButton(movieState.mediaDetailObject.toMediaObject(), movieViewModel)
    }
}

@Composable
fun InfoRow(modifier: Modifier = Modifier, movieState: MovieState.Data, onNavigateToReview: (MovieDetailObject) -> Unit) {
    Row(
        modifier = modifier
            //.padding(4.dp, 0.dp, 4.dp, 0.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Row for the stars so that we can use spaced evenly.
        Row(
            modifier = Modifier.clickable(onClick = { onNavigateToReview(movieState.mediaDetailObject) }),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val rating = movieState.mediaDetailObject.vote_average/2
            for (i in 1..5) {
                val starIcon = when {
                    i <= rating -> Icons.Outlined.Star
                    i <= rating + 0.5 -> Icons.AutoMirrored.Outlined.StarHalf
                    else -> Icons.Outlined.StarOutline
                }
                Image(
                    imageVector = starIcon,
                    modifier = Modifier.requiredSize(18.dp)
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
                String.format(Locale.ENGLISH, "%.1f", movieState.mediaDetailObject.vote_average/2),
                fontSize = 18.sp
            )
        }
        //Spacer(modifier = Modifier.weight(1f))
        Text(
            movieState.mediaDetailObject.relaseDate.substring(0, 4),
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.White,
                shadow = Shadow(color = Color.Black, blurRadius = 5.0f)

            )
        )
        //Spacer(modifier = Modifier.weight(1f))
        Text(
            movieState.mediaDetailObject.runTime.minutes.toString(),
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.White,
                shadow = Shadow(color = Color.Black, blurRadius = 5.0f)

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
    Box(modifier = Modifier
        .padding(20.dp, 10.dp, 20.dp, 10.dp)
        .clip(RoundedCornerShape(25.dp))
        .background(color = Color(0xFF353433))) {
        Text(
            text = description,
            style = TextStyle(
                lineHeight = 1.25.em,
                lineBreak = LineBreak.Paragraph,
                fontSize = 18.sp,
                textAlign = TextAlign.Justify,
                color = Color.White,
                shadow = Shadow(color = Color.Black, blurRadius = 7.5f)


            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 5.dp, 10.dp, 5.dp)

            ,

            )
    }
}
@Composable
fun BookmarkButton(media: MediaObject, movieViewModel: MovieViewModel){
    var isBookmarked by remember { mutableStateOf(false)}
    Image(
        imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
        modifier = Modifier
            .padding(100.dp)
            .clickable {
                movieViewModel.updateToDatabase(media, isBookmarked)
                isBookmarked = !isBookmarked
            },
        colorFilter = ColorFilter.tint(Color.White),
        contentDescription = "Bookmark"
    )
}


