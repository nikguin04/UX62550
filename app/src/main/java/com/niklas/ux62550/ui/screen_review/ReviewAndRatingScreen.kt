package com.niklas.ux62550.ui.screen_review

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.niklas.ux62550.models.Movie
import com.niklas.ux62550.navigation.ScreenWithGeneralNavBar
import com.niklas.ux62550.ui.theme.ReviewColor
import com.niklas.ux62550.ui.theme.UX62550Theme
import kotlin.time.Duration.Companion.minutes

@Composable
@Preview(showBackground = true, name = "ReviewAndRatingPreview")
fun ReviewAndRatingPreview(){
    val movie = Movie("RED: The Movie",
        "2090",
        3000.minutes,
        3.5,
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat ",
        listOf("Action", "Dinosaur Adventure", "Hentai"),
        13
    )
    UX62550Theme (darkTheme = true, dynamicColor = false) {
        ScreenWithGeneralNavBar {
            ScreenReviewAndRating(movie = movie)
        }
    }
}

@Composable
fun ScreenReviewAndRating(movie: Movie) {
    Column {
        ReviewLayout(movie.name, movie.rating)
        PublishReview()
        MoreDetailedReview()
    }
}

@Composable
fun ReviewLayout(MovieTitle: String, MovieRating: Double){
    Box(){
        Image(
            Icons.AutoMirrored.Outlined.ArrowBack,
            modifier = Modifier
                .padding(12.dp)
                .requiredSize(36.dp),
            colorFilter = ColorFilter.tint(Color.White),
            contentDescription = "Arrow back"
        )
        Box(Modifier.alpha(0.5f)) {
            Box(
                Modifier
                    .background(Color.Red)
                    .fillMaxWidth()
                    .height(230.dp))
        }
        ReviewText()
        TitleText(MovieTitle)
        Row(
            modifier = Modifier.align(Alignment.BottomCenter).
            padding(0.dp,186.dp,0.dp,0.dp),
            verticalAlignment = Alignment.CenterVertically,

        ) {
            for (i in 0..4) {
                Icon(
                    imageVector = Icons.Outlined.StarOutline,
                    contentDescription = "Star icon",
                    tint = ReviewColor,
                    modifier = Modifier
                        .requiredSize(54.dp)
                )
            }
            Text(MovieRating.toString() + "/5",
                style = TextStyle(
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Color.White,
                modifier = Modifier.padding(4.dp,0.dp,0.dp,0.dp)
            )
        }
    }
}
@Composable
fun PublishReview(){
    //TextField
    //Button
}

@Composable
fun MoreDetailedReview(){
    Text("More detailed review", color = ReviewColor)
    Row(
        modifier = Modifier.padding(4.dp,10.dp,0.dp,0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Music", color = Color.White)
        for(i in 0..4) {
            Image( //Needs to be made button
                imageVector = Icons.Outlined.StarOutline,
                modifier = Modifier.requiredSize(18.dp),
                colorFilter = ColorFilter.tint(Color.Yellow),
                contentDescription = "Star icon"
            )
        }
    }
    Row(
        modifier = Modifier.padding(4.dp,10.dp,0.dp,0.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text("Plot", color = Color.White)
        for(i in 0..4) {
            Image( //Needs to be made button
                imageVector = Icons.Outlined.StarOutline,
                modifier = Modifier.requiredSize(18.dp),
                colorFilter = ColorFilter.tint(Color.Yellow),
                contentDescription = "Star icon"
            )
        }
    }
    Row(
        modifier = Modifier.padding(4.dp,10.dp,0.dp,0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Acting", color = Color.White)
        for(i in 0..4) {
            Image( //Needs to be made button
                imageVector = Icons.Outlined.StarOutline,
                modifier = Modifier.requiredSize(18.dp),
                colorFilter = ColorFilter.tint(Color.Yellow),
                contentDescription = "Star icon"
            )
        }
    }
    Row(
        modifier = Modifier.padding(4.dp,10.dp,0.dp,0.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text("Directing", color = Color.White)
        for(i in 0..4) {
            Image( //Needs to be made button
                imageVector = Icons.Outlined.StarOutline,
                modifier = Modifier.requiredSize(18.dp),
                colorFilter = ColorFilter.tint(Color.Yellow),
                contentDescription = "Star icon"
            )
        }
    }
}
@Composable
fun ReviewText() {
    Text("Write a review for",
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            shadow = Shadow(
                color = Color.DarkGray, blurRadius = 10f
            ),
            textAlign = TextAlign.Center,
            color = ReviewColor
        ),
        modifier = Modifier.
            fillMaxWidth()
            .padding(0.dp, 90.dp, 0.dp, 0.dp),
    )
}
@Composable
fun TitleText(movieTitle: String) {
    Text(movieTitle,
        style = TextStyle(
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            shadow = Shadow(
                color = Color.Black, blurRadius = 10f
            ),
            textAlign = TextAlign.Center,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 130.dp, 0.dp, 0.dp),
    )
}