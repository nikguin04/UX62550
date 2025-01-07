package com.niklas.ux62550.ui.feature.mediadetails

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material.icons.automirrored.outlined.StarHalf
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.PlayCircleOutline
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.niklas.ux62550.data.examples.SearchDataExamples
import com.niklas.ux62550.data.model.Cast
import com.niklas.ux62550.data.model.Crew
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.data.remote.RemoteMediaDataSource.Companion.BASE_IMAGE_URL
import com.niklas.ux62550.ui.feature.common.CastState
import com.niklas.ux62550.ui.feature.common.CastViewModel
import com.niklas.ux62550.ui.feature.home.HorizontalLazyRowMovies
import com.niklas.ux62550.ui.theme.AwardAndDetailRating
import com.niklas.ux62550.ui.theme.DescriptionColor
import com.niklas.ux62550.ui.theme.UX62550Theme
import kotlin.time.Duration.Companion.minutes

@Composable
@Preview(showBackground = true)
fun MediaDetailPagePreview() {
    UX62550Theme(darkTheme = true) {
        Surface {
            MediaDetailsScreen(SearchDataExamples.MediaObjectExample, onNavigateToOtherMedia = {}, onNavigateToReview = {})
        }
    }
}

@Composable
fun MediaDetailsScreen(
    media: MediaObject,
    castViewModel: CastViewModel = viewModel(),
    onNavigateToOtherMedia: (MediaObject) -> Unit,
    onNavigateToReview: (String) -> Unit
) {
    val viewModel: MovieViewModel = viewModel(factory = MovieViewModelFactory(media = media))
    val movieState = viewModel.movieState.collectAsState().value
    val similarMediaState = viewModel.similarMediaState.collectAsState().value
    val trailerState = viewModel.trailerState.collectAsState().value
    val castState = castViewModel.castState.collectAsState().value
    val providerState = viewModel.providerState.collectAsState().value
    when (movieState) {
        MovieState.Empty -> {
            Text(text = "No data yet")// TODO make loading screen
        }
        is MovieState.Data -> {
            when (castState) {
                CastState.Empty -> {
                    Text("NO PIC")
                }
                is CastState.Data -> {
                    when(trailerState){
                        TrailerState.Empty ->{
                            Text("NO TRAILER")
                        }
                        is TrailerState.Data ->{
                            MediaDetailsContent(
                                movieState = movieState,
                                similarMediaState = similarMediaState,
                                trailerState = trailerState,
                                castState = castState,
                                providerState = providerState,
                                onNavigateToOtherMedia = onNavigateToOtherMedia,
                                onNavigateToReview = onNavigateToReview
                            )
                        }
                    }
                }
            }

        }
        else -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaDetailsContent(
    modifier: Modifier = Modifier,
    movieState: MovieState.Data,
    similarMediaState: SimilarMovieState,
    trailerState: TrailerState.Data,
    castState: CastState.Data,
    providerState: ProviderState,
    onNavigateToOtherMedia: (MediaObject) -> Unit,
    onNavigateToReview: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    )
    {
        Header(movieState = movieState, trailerState = trailerState)
        InfoRow(movieState = movieState, onNavigateToReview = onNavigateToReview)
        Genres(genres = movieState, providerState = providerState)
        DescriptionText(description = movieState.mediaDetailObjects.Description)
        ActorsAndDirectors(castState = castState)
        //Awards()
        DetailedRating()
        SimilarMedia(similarMediaState = similarMediaState, onNavigateToOtherMedia = onNavigateToOtherMedia)

    }
}

@Composable
fun Header(modifier: Modifier = Modifier, movieState: MovieState.Data, trailerState: TrailerState.Data) {
    val context = LocalContext.current

    var youtubeUrl = trailerState.trailerObject.resultsTrailerLinks.find { it.type == "Trailer" }?.let {
        "https://www.youtube.com/watch?v=${it.key}"
    }
    if(youtubeUrl == null){
        youtubeUrl = "https://www.youtube.com/watch?v=${trailerState.trailerObject.resultsTrailerLinks[0].key}"
    }

    Box(modifier = modifier.fillMaxWidth()) {
        // Background Image with Transparency
        Box(modifier = Modifier.alpha(0.5f)) {
            MovieImage(
                uri = movieState.mediaDetailObjects.backDropPath,
                modifier = Modifier.fillMaxWidth()
            )
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
                MovieImage(
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
        //Spacer(modifier = Modifier.weight(1f))
        Text(
            18.toString() + "+", //TODO PG RATING
            fontSize = 18.sp
        )
    }
}

@Composable
fun Genres(modifier: Modifier = Modifier, genres: MovieState.Data, providerState: ProviderState) {
    Row(
        modifier = modifier
            .padding(8.dp, 10.dp, 0.dp, 10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for ((index, genre) in genres.mediaDetailObjects.genre.withIndex().takeWhile { it.index <= 2 }) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(40.dp))
                    .background(Color.Gray)
                    .padding(8.dp, 4.dp)
            ) {
                Text(
                    text = genre.genreName,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            if ((index != 2) && (index != genres.mediaDetailObjects.genre.lastIndex)) {
                Spacer(modifier = Modifier.width(4.dp))
                DrawCircle(Modifier.size(10.dp), Color.LightGray)
                Spacer(modifier = Modifier.width(4.dp))
            }
        }

        Spacer(modifier = Modifier.weight(0.5f))
        when (providerState) {
            ProviderState.Empty -> {
                Text("NO PIC")
            }
            is ProviderState.Data -> {
                val country = providerState.providerDataObject["DK"]
                val StreamRentAndBuyProviderMap =
                    (country?.flatrate?.map { it.logoPath } ?: emptyList()) +
                        (country?.rent?.map { it.logoPath } ?: emptyList()) +
                        (country?.buy?.map { it.logoPath } ?: emptyList())

                if (country != null) {
                    for (i in 0 until minOf(3, StreamRentAndBuyProviderMap.size)) {
                        Spacer(modifier = Modifier.width(4.dp))
                        MovieImage(StreamRentAndBuyProviderMap[i],
                            modifier
                                .clip(CircleShape)
                                .size(32.dp))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.width(4.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActorsAndDirectors(modifier: Modifier = Modifier, castState: CastState.Data) {
    Text("Actors and Directors", Modifier.padding(8.dp, 12.dp, 0.dp, 0.dp))
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until minOf(4, castState.castObject.cast.size)) {
            MovieImage(
                castState.castObject.cast[i].castProfilePath,
                modifier
                    .clip(CircleShape)
                    .size(48.dp))
            Spacer(modifier = Modifier.width((6.dp)))
        }

        for (i in 0..2) { // Create clickable circles
            Spacer(modifier = Modifier.width(2.dp))
            IconButton(
                onClick = {
                    showBottomSheet = true
                },
                modifier = Modifier.size(12.dp) // Size of the clickable button area
            ) {
                DrawCircle(Modifier.size(10.dp), Color.LightGray) // Circle inside the button
            }
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = sheetState
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(castState.castObject.cast) { cast ->
                            CastRow(modifier, cast)
                        }
                        items(castState.castObject.crew) { crew ->
                            CrewRow(modifier, crew)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailedRating(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(8.dp, 12.dp, 0.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Needs to be made to a button later on
        Text("Detailed Rating", color = AwardAndDetailRating)
        val sheetState = rememberModalBottomSheetState()
        var showBottomSheet by remember { mutableStateOf(false) }
        IconButton(
            onClick = {
                showBottomSheet = true
            },
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowForwardIos,
                contentDescription = "Show bottom sheet",
                tint = Color.White
            )
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState
            ) {
                for (i in 1..4) {
                    Row(
                        modifier = Modifier.padding(50.dp, 0.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Category: $i")
                        Spacer(modifier = Modifier.width(12.dp))
                        Row {
                            var rating by remember { mutableIntStateOf(0) }
                            for (i in 1..5) {
                                val isFilled = i <= rating
                                val starIcon = if (isFilled) Icons.Outlined.Star else Icons.Outlined.StarOutline

                                Image(
                                    imageVector = starIcon,
                                    modifier = Modifier
                                        .requiredSize(18.dp),
                                    colorFilter = ColorFilter.tint(if (isFilled) Color.Yellow else Color.Gray), // Change color based on filled or not
                                    contentDescription = "Star icon"
                                )
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "$rating/5",
                                fontSize = 18.sp,
                                modifier = Modifier.weight(0.5f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(30.dp, 5.dp),
                        color = Color.Gray
                    )
                }
            }
        }
    }

}


@Composable
fun SimilarMedia(modifier: Modifier = Modifier, similarMediaState: SimilarMovieState, onNavigateToOtherMedia: (MediaObject) -> Unit) {
    Text("Movies similar to this one")
    when (similarMediaState) {
        SimilarMovieState.Empty -> {
            Text("NO PIC")
        }
        is SimilarMovieState.Data -> {
            HorizontalLazyRowMovies(modifier.fillMaxWidth(), 300.dp, 200.dp, similarMediaState.similarMoviesObject, onNavigateToOtherMedia)
        }
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

@Composable
fun DrawCircle(modifier: Modifier = Modifier, color: Color) {
    Box(
        modifier = modifier.drawBehind {
            // Set the radius based on the smaller of the box dimensions
            val radius = size.minDimension / 2
            drawCircle(
                color = color,
                radius = radius,
                center = center
            )
        }
    )
}

@Composable
fun MovieImage(uri: String?, modifier: Modifier = Modifier) {
    val imguri = if (uri != null) BASE_IMAGE_URL + uri else "https://cataas.com/cat"
    AsyncImage(
        model = imguri,
        contentDescription = null, // TODO: include content description
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun CastRow(modifier: Modifier, cast: Cast){
    Row(
        modifier = Modifier.padding(50.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MovieImage(cast.castProfilePath,
            modifier
                .clip(CircleShape)
                .size(64.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = cast.castName,
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(4.dp))
                DrawCircle(Modifier.size(6.dp), Color.LightGray)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = cast.character,
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

        }
    }
}
@Composable
fun CrewRow(modifier: Modifier, crew: Crew){
    Row(
        modifier = Modifier.padding(50.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MovieImage(crew.castProfilePath,
            modifier
                .clip(CircleShape)
                .size(64.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = crew.castName,
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(4.dp))
                DrawCircle(Modifier.size(6.dp), Color.LightGray)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = crew.job,
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

        }
    }
}