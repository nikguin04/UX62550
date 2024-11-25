package com.niklas.ux62550.ui.feature.mediadetails

import android.text.style.UnderlineSpan
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material.icons.automirrored.outlined.StarHalf
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.EmojiEvents
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
import com.niklas.ux62550.models.MediaItem
import com.niklas.ux62550.models.Movie
import com.niklas.ux62550.ui.feature.home.HorizontalLazyRowWithSnapEffect
import com.niklas.ux62550.ui.theme.AwardAndDetailRating
import com.niklas.ux62550.ui.theme.DescriptionColor
import com.niklas.ux62550.ui.theme.UX62550Theme

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
fun MediaDetailsScreen(viewModel: MovieViewModel = viewModel(), onNavigateToOtherMedia: (String) -> Unit, onNavigateToReview: (String) -> Unit) {
    val movie = viewModel.movieState.collectAsState().value
    val similarMedia = viewModel.similarMediaState.collectAsState().value
    MediaDetailsContent(movie = movie, similarMedia = similarMedia, onNavigateToOtherMedia = onNavigateToOtherMedia, onNavigateToReview = onNavigateToReview)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaDetailsContent(modifier: Modifier = Modifier, movie: Movie, similarMedia: List<MediaItem>, onNavigateToOtherMedia: (String) -> Unit, onNavigateToReview: (String) -> Unit) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    )  {
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
                TitleText(movie.name)
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
                    .align(Alignment.BottomStart)
                    .padding(4.dp, 0.dp, 8.dp, 0.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.clickable(onClick = { onNavigateToReview(movie.name) }),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (i in 1..5) {
                        val starIcon = when {
                            i <= movie.rating -> Icons.Outlined.Star
                            i <= movie.rating + 0.5 -> Icons.AutoMirrored.Outlined.StarHalf
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
                        movie.rating.toString(),
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    movie.year,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    movie.duration.toString(),
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    movie.pgRating.toString() + "+",
                    fontSize = 18.sp
                )
            }

        }
        Row(
            modifier = Modifier
                .padding(4.dp, 10.dp, 0.dp, 0.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for ((index, genre) in movie.genres.withIndex()) {
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
                if (index != movie.genres.lastIndex) {
                    Spacer(modifier = Modifier.width(4.dp))
                    DrawLittleCircle(Modifier.size(10.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
            Spacer(modifier = Modifier.weight(0.3f))
            repeat(3) { // Needs to be changed to Where to Watch
                Spacer(modifier = Modifier.width(4.dp))
                DrawLittleCircle(Modifier.size(10.dp))
            }
            Spacer(modifier = Modifier.weight(0.05f))
        }
        DescriptionText(movie.description)

        Text("Actors and Directors", modifier.padding(4.dp, 2.dp, 0.dp, 0.dp))
        val sheetState = rememberModalBottomSheetState()
        var showBottomSheet by remember { mutableStateOf(false) }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(4) {
                Spacer(modifier = Modifier.width(4.dp))
                DrawCircle(Modifier.size(64.dp), Color.Red)
            }
            Spacer(modifier = Modifier.width(2.dp))
            repeat(3) { // Create clickable circles
                Spacer(modifier = Modifier.width(2.dp))
                IconButton(
                    onClick = {
                        showBottomSheet = true
                    },
                    modifier = Modifier.size(12.dp) // Size of the clickable button area
                ) {
                    DrawLittleCircle(Modifier.size(10.dp)) // Circle inside the button
                }
                if (showBottomSheet) {
                    ModalBottomSheet(
                        scrimColor = Color.Transparent,
                        onDismissRequest = {
                            showBottomSheet = false
                        },
                        sheetState = sheetState
                    ) {
                        for (i in 0..3) {
                            Row(
                                modifier = Modifier.padding(50.dp, 0.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                DrawCircle(Modifier.size(65.dp), color = Color.Red)
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "Some Actor Name",
                                        fontSize = 14.sp,
                                        textDecoration= TextDecoration.Underline,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
//                                    HorizontalDivider(
//                                        modifier = Modifier.width(130.dp).padding(0.dp, 0.dp),
//                                        thickness = 0.5.dp,
//                                        color = Color.Gray
//                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))
                            HorizontalDivider(
                                modifier = Modifier.fillMaxWidth().padding(110.dp, 0.dp),
                                thickness = 0.5.dp,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier.padding(4.dp, 10.dp, 0.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = Icons.Outlined.EmojiEvents,
                modifier = Modifier.size(18.dp),
                colorFilter = ColorFilter.tint(Color.Yellow),
                contentDescription = "Star icon"
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text("Awards...", color = AwardAndDetailRating)
            val sheetState = rememberModalBottomSheetState()
            var showBottomSheet by remember { mutableStateOf(false) }

            // IconButton to trigger the Bottom Sheet
            IconButton(
                onClick = { showBottomSheet = true },
                modifier = Modifier.size(40.dp) // Set size directly on the IconButton if needed
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowForwardIos,
                    contentDescription = "Show bottom sheet",
                    tint = Color.White // Adjust color if necessary
                )
            }

            // Show the bottom sheet
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = sheetState
                ) {
                    for (i in 0..2) {
                        Row(
                            modifier = Modifier.padding(50.dp, 0.dp),
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                text = "Emmy ${2020 + i}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        HorizontalDivider(
                            modifier = Modifier.width(300.dp).padding(50.dp, 0.dp),
                            thickness = 0.5.dp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier.padding(4.dp, 10.dp, 0.dp, 0.dp),
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
                            modifier = Modifier.fillMaxWidth().padding(30.dp, 5.dp),
                            thickness = 0.5.dp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        Text("Movies similar to this one", modifier.padding(4.dp, 0.dp, 0.dp, 0.dp))
        HorizontalLazyRowWithSnapEffect(similarMedia, onNavigateToOtherMedia)
    }
}

@Composable
fun TitleText(movieTitle: String) {
    Text(
        text = movieTitle,
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            shadow = Shadow(
                color = Color.Black, blurRadius = 10f
            ),
            textAlign = TextAlign.Center,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 200.dp, 0.dp, 0.dp),
    )
}

@Composable
fun DescriptionText(movieDescription: String) {
    Text(
        text = movieDescription,
        style = TextStyle(
            lineHeight = 1.25.em,
            lineBreak = LineBreak.Paragraph,
            fontSize = 14.sp,
            textAlign = TextAlign.Start,
            color = DescriptionColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp, 0.dp, 0.dp, 0.dp),
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
fun DrawLittleCircle(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.drawBehind {
            // Set the radius based on the smaller of the box dimensions
            val radius = size.minDimension / 2
            drawCircle(
                color = Color.LightGray,
                radius = radius,
                center = center
            )
        }
    )
}
