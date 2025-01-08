
package com.niklas.ux62550.ui.feature.mediadetails

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.drawWithContent
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.niklas.ux62550.data.examples.SearchDataExamples
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.ui.feature.common.CreditState
import com.niklas.ux62550.ui.feature.common.CreditViewModel
import com.niklas.ux62550.ui.feature.common.CreditsViewModelFactory
import com.niklas.ux62550.ui.feature.home.MediaItem
import com.niklas.ux62550.ui.theme.DescriptionColor
import com.niklas.ux62550.ui.theme.UX62550Theme
import kotlin.time.Duration.Companion.minutes

@Composable
@Preview(showBackground = true)
fun MediaDetailPagePreview() {
    val media = SearchDataExamples.MediaObjectExample
    val movieViewModel: MovieViewModel = viewModel(factory = MovieViewModelFactory(media = media))
    movieViewModel.initPreview()

    val creditsViewModel: CreditViewModel = viewModel(factory = CreditsViewModelFactory(media = media))

    UX62550Theme(darkTheme = true) {
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
    onNavigateToReview: (String) -> Unit
) {

    val movieState = movieViewModel.movieState.collectAsState().value
    val similarMediaState = movieViewModel.similarMediaState.collectAsState().value
    val trailerState = movieViewModel.trailerState.collectAsState().value
    val creditState = creditsViewModel.creditState.collectAsState().value
    val providerState = movieViewModel.providerState.collectAsState().value

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    )
    {
        when (movieState) {
            MovieState.Empty -> {
                Text(text = "No data yet")// TODO make loading screen
            }
            is MovieState.Data -> {
                Header(movieState = movieState, trailerState = trailerState)
                InfoRow(movieState = movieState, onNavigateToReview = onNavigateToReview)
                Genres(genres = movieState, providerState = providerState)
                DescriptionText(description = movieState.mediaDetailObjects.Description)
            }
            else -> {}
        }

        when (creditState) {
            CreditState.Empty -> {
                Text("No credits data yet")
            }
            is CreditState.Data -> {
                ActorsAndDirectors(creditState = creditState)
            }
        }
        DetailedRating()
        SimilarMedia(similarMediaState = similarMediaState, onNavigateToOtherMedia = onNavigateToOtherMedia)
    }
}

@Composable
fun Header(modifier: Modifier = Modifier, movieState: MovieState.Data, trailerState: TrailerState) {
    val context = LocalContext.current
    Box(modifier = modifier.fillMaxWidth()) {
        // Background Image with Transparency
        Box(modifier = Modifier.alpha(0.5f)) {
            MediaItem(
                uri = movieState.mediaDetailObjects.backDropPath,
                modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer(alpha = 1f)
                .drawWithContent {
                    drawContent() // Draw the actual image

                    // Draw the fade
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Black,
                                Color.Black,
                                Color.Black.copy(alpha = 0.5f),
                                Color.Transparent,
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY // Adjust for gradient depth
                        ),
                        blendMode = androidx.compose.ui.graphics.BlendMode.DstIn
                    )
                }


            )
        }
    when (trailerState) {
        TrailerState.Empty -> {

        }
        is TrailerState.Data -> {
            var youtubeUrl = trailerState.trailerObject.resultsTrailerLinks.find { it.type == "Trailer" }?.let {
                "https://www.youtube.com/watch?v=${it.key}"
            }
            if(youtubeUrl == null){
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
                    MediaItem(
                        uri = movieState.mediaDetailObjects.backDropPath,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f)
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
                TitleText(movieState.mediaDetailObjects.Originaltitle)
            }
        }
         else -> {}
    }

        // Bookmark Button
        Image(
            Icons.Outlined.BookmarkBorder,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(20.dp),
            colorFilter = ColorFilter.tint(Color.White),
            contentDescription = "Bookmark"
        )
    }
}

@Composable
fun InfoRow(modifier: Modifier = Modifier, movieState: MovieState.Data, onNavigateToReview: (String) -> Unit) {
    Row(
        modifier = modifier
            //.padding(4.dp, 0.dp, 4.dp, 0.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Row for the stars so that we can use spaced evenly.
        Row(
            modifier = Modifier.clickable(onClick = { onNavigateToReview(movieState.mediaDetailObjects.Originaltitle) }),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val rating = movieState.mediaDetailObjects.rating / 2
            for (i in 1..5) {
                val starIcon = when {
                    i <= rating -> Icons.Outlined.Star
                    i <= rating + 0.5 -> Icons.AutoMirrored.Outlined.StarHalf
                    else -> Icons.Outlined.StarOutline
                }
                Image(
                    imageVector = starIcon,
                    modifier = Modifier.requiredSize(18.dp),
                    colorFilter = ColorFilter.tint(Color.Yellow),
                    contentDescription = "Rating star"
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                rating.toString().substring(0,3),
                fontSize = 18.sp
            )
        }
        //Spacer(modifier = Modifier.weight(1f))
        Text(
            movieState.mediaDetailObjects.relaseDate.substring(0, 4),
            fontSize = 18.sp
        )
        //Spacer(modifier = Modifier.weight(1f))
        Text(
            movieState.mediaDetailObjects.runTime.minutes.toString(),
            fontSize = 18.sp
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
    Text(
        text = description,
        style = TextStyle(
            lineHeight = 1.25.em,
            lineBreak = LineBreak.Paragraph,
            fontSize = 14.sp,
            textAlign = TextAlign.Start,
            color = DescriptionColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 6.dp, 0.dp, 0.dp),
    )
}

