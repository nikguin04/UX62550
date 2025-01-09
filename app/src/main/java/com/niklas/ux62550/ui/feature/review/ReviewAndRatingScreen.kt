package com.niklas.ux62550.ui.feature.review


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.niklas.ux62550.data.examples.MediaDetailExample
import com.niklas.ux62550.data.model.MovieDetailObject
import com.niklas.ux62550.ui.feature.home.ImageSize
import com.niklas.ux62550.ui.feature.home.MediaItem
import com.niklas.ux62550.ui.theme.ReviewColor
import com.niklas.ux62550.ui.theme.TextFieldColor
import com.niklas.ux62550.ui.theme.UX62550Theme

@Composable
@Preview(showBackground = true)
fun ReviewAndRatingPreview() {

    UX62550Theme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            ScreenReviewAndRating(media = MediaDetailExample.MediaDetailObjectExample)
        }
    }
}

@Composable
fun ScreenReviewAndRating(
    modifier: Modifier = Modifier,
    media: MovieDetailObject)
    {

    Column(modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        ReviewLayout(media = media)
        PublishReview()
        MoreDetailedReview()
    }
}

@Composable
fun ReviewLayout(
    modifier: Modifier = Modifier,
    media: MovieDetailObject
) {
    Column {
        Box{
            val backColor = MaterialTheme.colorScheme.background
            MediaItem(
                uri = media.backDropPath,
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer(alpha = 1f)
                    .drawWithContent {
                        drawContent() // Draw the actual image

                        // Draw the fade
                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    backColor,
                                    backColor,
                                    backColor.copy(alpha = 0.5f),
                                    Color.Transparent,
                                ),
                                startY = 0f,
                                endY = Float.POSITIVE_INFINITY // Adjust for gradient depth
                            ),
                            blendMode = androidx.compose.ui.graphics.BlendMode.DstIn
                        )
                    },
                    size = ImageSize.BACKDROP
            )
            ReviewText()
            TitleText(media.Originaltitle)

        }
//        Box(
//            modifier = Modifier
//                .size(
//                    LocalConfiguration.current.screenWidthDp.dp,
//                    LocalConfiguration.current.screenWidthDp.dp / 3 * 2
//                )
//
//        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                Row(
                    //modifier = Modifier
                    //.align(Alignment.BottomCenter),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    var rating by remember { mutableStateOf(0) }
                    for (i in 0..4) {
                        val isFilled = i <= rating
                        Image(
                            // Needs to be made button
                            imageVector = Icons.Outlined.StarOutline,
                            modifier = Modifier.requiredSize(54.dp)
                                .clickable {
                                    rating = i
                                },
                            colorFilter = ColorFilter.tint(if (isFilled) Color.Yellow else Color.Gray),
                            contentDescription = "Star icon",

                            )
                    }
                    Text(
                        text = "${rating+1}/5",
                        style = TextStyle(
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.White,
                        modifier = Modifier.padding(4.dp, 0.dp, 4.dp, 0.dp)
                    )
                }
            }

        }
    }

@Composable
fun PublishReview() {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        shape = RoundedCornerShape(15),
        modifier = Modifier
            .requiredSize(400.dp, 200.dp)
            .padding(20.dp, 10.dp),

        colors = TextFieldDefaults.colors(
            focusedContainerColor = TextFieldColor,
            unfocusedContainerColor = TextFieldColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedLabelColor = Color.Black,
            focusedTextColor =  Color.Black

        ),
        label = { Text("Write a review with accordance to the rating") },
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
    Text(
        text = "More detailed review",
        color = ReviewColor,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth().padding(0.dp, 10.dp)
    )

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
        var rating by remember { mutableStateOf(0) }
        for (i in 0..4) {
            val isFilled = i <= rating
            Image(
                // Needs to be made button
                imageVector = Icons.Outlined.StarOutline,
                modifier = Modifier.requiredSize(34.dp)
                    .clickable {
                        rating = i
                    },
                colorFilter = ColorFilter.tint(if (isFilled) Color.Yellow else Color.Gray),
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
        var rating by remember { mutableStateOf(0) }

        for (i in 0..4) {
            val isFilled = i <= rating
            Image(
                // Needs to be made button
                imageVector = Icons.Outlined.StarOutline,
                modifier = Modifier.requiredSize(34.dp)
                    .clickable {
                        rating = i
                    },
                colorFilter = ColorFilter.tint(if (isFilled) Color.Yellow else Color.Gray),
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
        var rating by remember { mutableStateOf(0) }

        for (i in 0..4) {
            val isFilled = i <= rating
            Image(
                // Needs to be made button
                imageVector = Icons.Outlined.StarOutline,
                modifier = Modifier.requiredSize(34.dp)
                    .clickable {
                        rating = i
                    },
                colorFilter = ColorFilter.tint(if (isFilled) Color.Yellow else Color.Gray),
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
        var rating by remember { mutableStateOf(0) }

        for (i in 0..4) {
            val isFilled = i <= rating
            Image(
                // Needs to be made button
                imageVector = Icons.Outlined.StarOutline,
                modifier = Modifier.requiredSize(34.dp)
                    .clickable {
                        rating = i
                    },
                colorFilter = ColorFilter.tint(if (isFilled) Color.Yellow else Color.Gray),
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
            .padding(0.dp, 90.dp)
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
            .padding(0.dp, 120.dp),
    )
}