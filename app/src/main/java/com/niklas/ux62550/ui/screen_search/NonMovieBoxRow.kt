package com.niklas.ux62550.ui.screen_search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.niklas.ux62550.models.NonMovieBox
//import com.niklas.ux62550.screen.nonMovieBoxMoviePicture

@Composable
fun nonMovieBoxMoviePicture(width: Dp, height: Dp, round: Dp, color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(color)
            .size(width, height)
            .padding()
    )
}

@Composable
fun NonMovieBoxRow(nonMovieBox: NonMovieBox, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
        //.background(Color.White, shape= RoundedCornerShape(8.dp))
        //.padding(8.dp)
    ) {

        nonMovieBoxMoviePicture(
            width = 40.dp,
            height = 40.dp,
            round = 12.dp,
            color = nonMovieBox.tempColor,
            modifier = Modifier.padding(end = 8.dp)
        )

        Row (
            modifier = Modifier
                //.fillMaxWidth()
                .align(Alignment.CenterVertically)
                .padding(start = 16.dp)
        ) {

            Text(
                text = nonMovieBox.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = " | ",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = nonMovieBox.genre,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}