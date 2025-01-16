package com.niklas.ux62550.ui.feature.mediadetails

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
    modifier: Modifier = Modifier,
    similarMediaState: SimilarMovieState,
    onNavigateToOtherMedia: (MediaObject) -> Unit) {

    Text("Movies similar to this one", style = TextStyle(
        fontSize = 18.sp,
        color = Color.White,
        shadow = Shadow(color = Color.Black, blurRadius = 5.0f)
    ),
        modifier = modifier.padding(20.dp, 0.dp, 20.dp, 0.dp))
    when (similarMediaState) {
        SimilarMovieState.Empty -> {
            Text("NO PIC")
        }
        is SimilarMovieState.Data -> {
            HorizontalLazyRowMovies(
                Modifier.padding(0.dp, 0.dp),
                Dp(255f),
                Dp(255f/16*9),
                similarMediaState.similarMoviesObject,
                onNavigateToOtherMedia,
                fetchEnBackdrop = true
            )
        }
    }
}