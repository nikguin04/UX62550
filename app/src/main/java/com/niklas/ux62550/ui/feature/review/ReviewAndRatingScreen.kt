package com.niklas.ux62550.ui.feature.review


import ReviewViewModel
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.niklas.ux62550.data.examples.MediaDetailExample
import com.niklas.ux62550.data.model.MovieDetailObject
import com.niklas.ux62550.ui.feature.common.ImageSize
import com.niklas.ux62550.ui.feature.common.MediaItem
import com.niklas.ux62550.ui.theme.ReviewColor
import com.niklas.ux62550.ui.theme.TextFieldColor
import com.niklas.ux62550.ui.theme.UX62550Theme

@Composable
@Preview(showBackground = true)
fun ReviewAndRatingPreview() {
    UX62550Theme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ScreenReviewAndRating(media = MediaDetailExample.MediaDetailObjectExample, navBack = {})

        }

    }
}

@Composable
fun ScreenReviewAndRating(
    modifier: Modifier = Modifier,
    media: MovieDetailObject,
    navBack: () -> Unit,
    reviewViewModel: ReviewViewModel = viewModel()
)
{
    val reviewState by reviewViewModel.reviewState.collectAsState()

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Header(media = media, reviewViewModel)

        PublishReview(stars = reviewState.rating,
            reviewText = reviewState.reviewText,
            onRatingChange = { newRating -> reviewViewModel.updateRating(newRating) },
            onReviewChange = { newReview -> reviewViewModel.updateReviewText(newReview) },
            onSubmit = {
                reviewViewModel.submitReview(mediaId = media.id)
                navBack()
            })

        MoreDetailedReview(reviewViewModel)
    }
}



@Composable
fun Header(
    media: MovieDetailObject,
    reviewViewModel: ReviewViewModel
) {
    Box {
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
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically

        ) {
            RatingStars(
                rating = reviewViewModel.reviewState.collectAsState().value.rating,

                onRatingSelected = { reviewViewModel.updateRating(it) },
                starSize = 40.dp
            )
            val currentRating = reviewViewModel.reviewState.collectAsState().value.rating
            Text(
                text = "${currentRating}/5",
                style = TextStyle(
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Color.White,
                modifier = Modifier.padding(4.dp, 0.dp, 0.dp, 0.dp)
            )

        }
    }


@Composable
fun PublishReview(
    stars: Float,
    reviewText: String,
    onRatingChange: (Float) -> Unit,
    onReviewChange: (String) -> Unit,
    onSubmit: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = reviewText,
            onValueChange = { onReviewChange(it) },
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
                focusedTextColor = Color.Black
            ),
            label = { Text("Write a review with accordance to the rating") },
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = onSubmit,
                modifier = Modifier.width(150.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ReviewColor),
            ) {
                Text("Publish", color = Color.White)
            }
        }
    }
}

@Composable
fun MoreDetailedReview(reviewViewModel: ReviewViewModel) {
    val reviewState by reviewViewModel.reviewState.collectAsState()

    val categoryRatings = reviewState.categoryRatings

    Column(modifier = Modifier.padding(16.dp)) {
        ReviewViewModel.ReviewCategoryList.forEach { category ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$category:",
                    modifier = Modifier.weight(1f),
                    fontSize = 25.sp
                )

                val categoryRating = categoryRatings[category] ?: 0f

                RatingStars(
                    rating = categoryRating,
                    onRatingSelected = { newRating ->
                        reviewViewModel.updateCategoryRating(category, newRating)
                    }
                )
            }
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
            .padding(0.dp, 120.dp)
    )
}


@Composable
fun RatingStars(rating: Float, onRatingSelected: (Float) -> Unit,  starSize: Dp = 34.dp) {
    Row(modifier = Modifier.wrapContentWidth()) {
        for (i in 0..4) {
            val isFilled = i < rating.toInt()
            val isHalfFilled = (rating - i) in 0.5..0.99

            Image(
                imageVector = when {
                    isFilled -> Icons.Filled.Star
                    isHalfFilled -> Icons.AutoMirrored.Filled.StarHalf
                    else -> Icons.Outlined.StarOutline
                },
                modifier = Modifier
                    .requiredSize(starSize)
                    .clickable {
                        val clickedPosition = i + 1
                        val newRating = if (rating == clickedPosition.toFloat()) i + 0.5f else clickedPosition.toFloat()
                        onRatingSelected(newRating)
                    },
                colorFilter = ColorFilter.tint(if (isFilled || isHalfFilled) Color.Yellow else Color.Gray),
                contentDescription = "Star icon",
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}




