package com.niklas.ux62550.ui.feature.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.models.Movie
import com.niklas.ux62550.ui.feature.common.MediaItem
import com.niklas.ux62550.ui.feature.mediadetails.MovieImage

@Composable
fun MovieBoxMoviePicture(width: Dp, height: Dp, round: Dp, color: Color, text: String, textColor: Color, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(round))
            .background(color)
            .size(width, height)
            .padding()
    ) {
        Text(
            text = text,
            color = textColor,
            modifier = Modifier.align(Alignment.Center)
                .padding(7.5.dp, 0.dp)
        )
    }
}

@Composable
fun MovieBoxRow(movie: MediaObject, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        if (movie.backdrop_path == null) {
            MovieBoxMoviePicture(90.dp, 50.62.dp, 0.dp, Color.Black, "No image available", Color.White)
        }
        else {
            MediaItem(
                uri = movie.backdrop_path,
                round = 0.dp,
                modifier = Modifier.width(90.dp).height(50.62.dp)
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 16.dp)
        ) {
            Row {
                Column {

                    Text(
                        text = movie.title,
                        modifier = Modifier.width(150.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
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
                            text = movie.vote_average?.div(2).toString() + "/5", // Conveting the 10/10 to an 5/5 rating system
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Text(
                    text = " · ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                if(movie.release_date.toString().count() >= 5){
                    Text(
                        text = movie.release_date.toString().substring(0, 4),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                else {
                    Text(
                        text = "unknown",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
