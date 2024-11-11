package com.niklas.ux62550.modules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.niklas.ux62550.models.MovieBox
//import com.niklas.ux62550.screen.movieBoxMoviePicture

@Composable
fun movieBoxMoviePicture(width: Dp, height: Dp, round: Dp, color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.clip(RoundedCornerShape(round)).background(color).size(width, height).padding()
    )
}

@Composable
fun MovieBoxRow(movieBox: MovieBox, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
        //.background(Color.White, shape= RoundedCornerShape(8.dp))
        //.padding(8.dp)
    ) {

        movieBoxMoviePicture(
            width = 90.dp,
            height = 50.62.dp,
            round = 12.dp,
            color = movieBox.tempColor,
            modifier = Modifier.padding(end = 8.dp)
        )

        Column (
            modifier = Modifier
                //.fillMaxWidth()
                .align(Alignment.CenterVertically)
                .padding(start = 16.dp)
        ) {
            Row {
                Column {
                    Text(
                        text = movieBox.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = movieBox.rating.toString() + "/5",
                        //text =  "3/5",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = " | ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = movieBox.genre,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}