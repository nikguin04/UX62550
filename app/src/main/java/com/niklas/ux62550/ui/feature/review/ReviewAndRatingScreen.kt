package com.niklas.ux62550.ui.feature.review

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.niklas.ux62550.ui.theme.ReviewColor
import com.niklas.ux62550.ui.theme.TextFieldColor
import com.niklas.ux62550.ui.theme.UX62550Theme
import kotlin.time.Duration.Companion.minutes

@Composable
@Preview(showBackground = true)
fun ReviewAndRatingPreview() {
    val movie = Movie(
        name = "RED: The Movie",
        year = "2090",
        duration = 3000.minutes,
        rating = 3.5,
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat ",
        genres = listOf("Action", "Dinosaur Adventure", "Hentai"),
        pgRating = 13,
        tempColor = Color.Red
    )
    UX62550Theme(darkTheme = true, dynamicColor = false) {
        Surface(modifier = Modifier.fillMaxSize()) {
            ScreenReviewAndRating(movie = movie)
        }
    }
}

@Composable
fun ReviewScreen() {
    val movie = Movie(
        name = "RED: The Movie",
        year = "2090",
        duration = 3000.minutes,
        rating = 3.5,
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat ",
        genres = listOf("Action", "Dinosaur Adventure", "Hentai"),
        pgRating = 13,
        tempColor = Color.Red
    )
    ScreenReviewAndRating(movie = movie)
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
fun ReviewLayout(movieTitle: String, movieRating: Double) {
    Box {
        Box(
            modifier = Modifier
                .alpha(0.3f)
                .background(Color.Red)
                .fillMaxWidth()
                .height(230.dp)
        )
        ReviewText()
        TitleText(movieTitle)
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(0.dp, 186.dp, 0.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 0..4) {
                Icon(
                    imageVector = Icons.Outlined.StarOutline,
                    contentDescription = "Star icon",
                    tint = ReviewColor,
                    modifier = Modifier.requiredSize(54.dp)
                )
            }
            Text(
                text = "$movieRating/5",
                style = TextStyle(
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Color.White,
                modifier = Modifier.padding(4.dp, 0.dp, 0.dp, 0.dp)
            )
        }
    }
}

@Composable
fun PublishReview() {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier
            .requiredSize(400.dp, 200.dp)
            .padding(20.dp, 10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = TextFieldColor,
            focusedContainerColor = TextFieldColor,
        ),
        prefix = { Text("Write a review with accordance to the rating") },
        textStyle = TextStyle(
            fontSize = 12.sp,
            color = Color.Black
        )


    )
    Box(
        modifier = Modifier.fillMaxWidth().padding(0.dp, 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { TODO("functionality") },
            modifier = Modifier.width(150.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ReviewColor),
        ) {
            Text("Publish", color = Color.White)
        }
    }
}

@Composable
fun MoreDetailedReview() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "More detailed review",
            color = ReviewColor,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(40.dp, 10.dp)
        )
    }
    Row(
        modifier = Modifier.fillMaxWidth().padding(30.dp, 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Music:",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        for (i in 0..4) {
            Image(
                // Needs to be made button
                imageVector = Icons.Outlined.StarOutline,
                modifier = Modifier.requiredSize(34.dp),
                colorFilter = ColorFilter.tint(Color.Yellow),
                contentDescription = "Star icon",
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth().padding(30.dp, 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Plot:",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        for (i in 0..4) {
            Image(
                // Needs to be made button
                imageVector = Icons.Outlined.StarOutline,
                modifier = Modifier.requiredSize(34.dp),
                colorFilter = ColorFilter.tint(Color.Yellow),
                contentDescription = "Star icon",
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth().padding(30.dp, 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Acting:",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        for (i in 0..4) {
            Image(
                // Needs to be made button
                imageVector = Icons.Outlined.StarOutline,
                modifier = Modifier.requiredSize(34.dp),
                colorFilter = ColorFilter.tint(Color.Yellow),
                contentDescription = "Star icon",
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth().padding(30.dp, 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Directing:",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        for (i in 0..4) {
            Image(
                // Needs to be made button
                imageVector = Icons.Outlined.StarOutline,
                modifier = Modifier.requiredSize(34.dp),
                colorFilter = ColorFilter.tint(Color.Yellow),
                contentDescription = "Star icon",
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}

@Composable
fun ReviewText() {
    Text(
        text = "Write a review for",
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            shadow = Shadow(
                color = Color.Black, blurRadius = 2f
            ),
            textAlign = TextAlign.Center,
            color = ReviewColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 90.dp, 0.dp, 0.dp),
    )
}

@Composable
fun TitleText(movieTitle: String) {
    Text(
        text = movieTitle,
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
