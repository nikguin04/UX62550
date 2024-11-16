package com.niklas.ux62550.ui.screen_search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.niklas.ux62550.models.Movie

@Composable
fun movieBoxMoviePicture(width: Dp, height: Dp, round: Dp, color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.clip(RoundedCornerShape(round)).background(color).size(width, height).padding()
    )
}

@Composable
fun MovieBoxRow(movie: Movie, modifier: Modifier = Modifier) {
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
            color = movie.tempColor,
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
                        text = movie.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row (modifier
                        .padding(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)

                    ){
                        Image( //Needs to be made button
                            imageVector = Icons.Filled.Star,
                            modifier = Modifier.requiredSize(18.dp),
                            colorFilter = ColorFilter.tint(Color.Yellow),
                            contentDescription = "Star icon"
                        )
                        Text(
                            text = movie.rating.toString() + "/5",
                            //text =  "3/5",
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
                Text(
                    text = movie.year,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
