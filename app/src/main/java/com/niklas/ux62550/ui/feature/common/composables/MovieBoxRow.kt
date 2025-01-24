package com.niklas.ux62550.ui.feature.common.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.ui.feature.common.ImageSize
import com.niklas.ux62550.ui.feature.common.MediaDetailFetchViewModel
import com.niklas.ux62550.ui.feature.common.MediaDetailFetchViewModelFactory
import com.niklas.ux62550.ui.feature.common.MediaItem
import com.niklas.ux62550.ui.feature.mediadetails.MovieState

@Composable
fun MovieBoxRow(movie: MediaObject, modifier: Modifier = Modifier, infoRowModifier: Modifier = Modifier) {
    Row(modifier.fillMaxWidth()) {
        MediaItem(
            uri = movie.backdrop_path,
            size = ImageSize.BACKDROP,
            modifier = Modifier
                .width(110.dp)
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(6.dp))
        )
        Column(
            modifier = infoRowModifier
                .align(Alignment.CenterVertically)
        ) {
            Row {
                Column {
                    Text(
                        text = movie.title,
                        modifier = Modifier.width(210.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 8,
                        overflow = TextOverflow.Ellipsis
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Image( // Needs to be made button
                            imageVector = Icons.Filled.Star,
                            modifier = Modifier.requiredSize(18.dp),
                            colorFilter = ColorFilter.tint(Color.Yellow),
                            contentDescription = "Star icon"
                        )
                        Text(
                            text = String.format("%.1f", movie.vote_average?.div(2)) + "/5", // Converting the 10/10 to an 5/5 rating system
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.width(62.dp)) // TODO: Fix this, why is it hardcoded

                        Text(
                            text = if (movie.release_date.count() > 4) {
                                movie.release_date.toString().substring(0, 4)
                            } else {
                                "N/A"
                            },
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun MovieBoxRowFromIdErrorPreview() {
    MovieBoxRowFromId(Modifier, 0, {})
}

@Composable
fun MovieBoxRowFromId(modifier: Modifier = Modifier, movieId: Int, onNavigateToMedia: (MediaObject) -> Unit, infoRowModifier: Modifier = Modifier) {
    val mediaDetailFetchViewModel: MediaDetailFetchViewModel = viewModel(
        factory = MediaDetailFetchViewModelFactory(movieId),
        key = movieId.toString()
    )

    val mediaState = mediaDetailFetchViewModel.movieState.collectAsState().value
    when (mediaState) {
        is MovieState.Empty -> {
            Text("Loading")
        }
        is MovieState.Data -> {
            MovieBoxRow(
                movie = mediaState.mediaDetailObject.toMediaObject(),
                modifier = modifier.clickable { onNavigateToMedia(mediaState.mediaDetailObject.toMediaObject()) },
                infoRowModifier = infoRowModifier
            )
        }
        is MovieState.Error -> {
            Text("Network error")
        }
    }
}
