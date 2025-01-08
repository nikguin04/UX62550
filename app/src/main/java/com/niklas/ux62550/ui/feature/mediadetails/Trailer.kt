package com.niklas.ux62550.ui.feature.mediadetails

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayCircleOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.niklas.ux62550.ui.feature.home.MediaItem

@Composable
fun MediaTrailer(modifier: Modifier, trailerState: TrailerState, movieState: MovieState.Data){
    val context = LocalContext.current
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
}