package com.niklas.ux62550.ui.feature.mediadetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.ui.feature.home.HorizontalLazyRowMovies

@Composable
fun SimilarMedia(
    similarMediaState: SimilarMovieState,
    onNavigateToOtherMedia: (MediaObject) -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        "Movies similar to this one",
        style = TextStyle(
            fontSize = 18.sp,
            color = Color.White,
            shadow = Shadow(blurRadius = 5f)
        ),
        modifier = modifier.padding(start = 20.dp)
    )
    Box(Modifier.padding(bottom = 20.dp)) {
        when (similarMediaState) {
            is SimilarMovieState.Empty -> {
                Text("NO PIC")
            }
            is SimilarMovieState.Error -> {
                Text("Network error")
            }
            is SimilarMovieState.Data -> {
                HorizontalLazyRowMovies(
                    width = Dp(255f),
                    height = Dp(255f / 16 * 9),
                    edgeGap = 20.dp,
                    betweenGap = 12.dp,
                    items = similarMediaState.similarMoviesObject,
                    onNavigateToMedia = onNavigateToOtherMedia,
                    fetchEnBackdrop = true
                )
            }
        }
    }
}
