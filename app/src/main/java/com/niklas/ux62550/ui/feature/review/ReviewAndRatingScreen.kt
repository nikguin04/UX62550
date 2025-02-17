package com.niklas.ux62550.ui.feature.review

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.CompositingStrategy
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
import com.niklas.ux62550.navigation.GeneralTopBar
import com.niklas.ux62550.ui.feature.common.ImageSize
import com.niklas.ux62550.ui.feature.common.MediaItem
import com.niklas.ux62550.ui.theme.ReviewColor
import com.niklas.ux62550.ui.theme.TextFieldColor
import com.niklas.ux62550.ui.theme.UX62550Theme
import kotlin.math.roundToInt

@Composable
fun ScreenReviewAndRating(
    media: MovieDetailObject,
    navigateBack: () -> Unit,
    showSnackbar: (String) -> Unit,
    topModifier: Modifier = Modifier,
    reviewViewModel: ReviewViewModel = viewModel()
) {
    val reviewState by reviewViewModel.reviewState.collectAsState()

    Box {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            Header(modifier = topModifier, media = media, reviewViewModel = reviewViewModel)

            PublishReview(
                reviewText = reviewState.reviewText,
                onReviewChange = { newReview -> reviewViewModel.updateReviewText(newReview) },
                onSubmit = {
                    reviewViewModel.submitReview(mediaId = media.id, onSuccess = { showSnackbar("Successfully submitted review") }, onError = { showSnackbar("Failed to submit review") })
                    navigateBack()
                }
            )

            MoreDetailedReview(reviewViewModel)
        }

        GeneralTopBar(navigateBack = navigateBack)
    }
}

@Composable
fun Header(
    media: MovieDetailObject,
    reviewViewModel: ReviewViewModel,
    modifier: Modifier = Modifier,
) {
    Box {
        MediaItem(
            uri = media.backdropPath,
            size = ImageSize.BACKDROP,
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                .drawWithContent {
                    drawContent() // Draw the actual image
                    drawRect( // Draw the fade
                        brush = Brush.verticalGradient(
                            0f to Color.Black,
                            1 / 3f to Color.Black,
                            1f to Color.Transparent,
                        ),
                        blendMode = BlendMode.DstIn
                    )
                }
        )
        Column(modifier = modifier) {
            ReviewText()
            TitleText(media.title)

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp),
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
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}

@Composable
fun PublishReview(
    reviewText: String,
    onReviewChange: (String) -> Unit,
    onSubmit: () -> Unit,
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
                Text(
                    text = "Publish",
                    style = TextStyle(
                        shadow = Shadow(blurRadius = 10f)
                    ),
                    color = Color.White
                )
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
            shadow = Shadow(blurRadius = 2f),
            textAlign = TextAlign.Center,
            color = ReviewColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 120.dp)
    )
}

@Composable
fun TitleText(movieTitle: String) {
    Text(
        text = movieTitle,
        style = TextStyle(
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            shadow = Shadow(blurRadius = 10f),
            textAlign = TextAlign.Center
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
    )
}

// Used ChatGPT how to make stars bigger other places as a variable
@Composable
fun RatingStars(
    rating: Float,
    onRatingSelected: (Float) -> Unit,
    starSize: Dp = 34.dp
) {
    var currentRating by remember { mutableFloatStateOf(rating) }

    Box {
        Row(
            modifier = Modifier
                .wrapContentWidth()
        ) {
            for (i in 0..4) {
                val isFilled = i + 1 <= currentRating.toInt()
                val isHalfFilled = (currentRating - i) >= 0.5f

                Image(
                    imageVector = when {
                        isFilled -> Icons.Filled.Star
                        isHalfFilled -> Icons.AutoMirrored.Filled.StarHalf
                        else -> Icons.Outlined.StarOutline
                    },
                    modifier = Modifier.requiredSize(starSize),
                    colorFilter = ColorFilter.tint(
                        if (isFilled || isHalfFilled) Color.Yellow else Color.Gray
                    ),
                    contentDescription = "Star icon",
                )
                Spacer(Modifier.width(4.dp))
            }
        }

        Slider(
            modifier = Modifier.requiredSize(starSize * 5, starSize),
            value = 5f,
            onValueChange = { currentRating = it.roundToInt().toFloat() / 2; onRatingSelected(currentRating) },
            colors = SliderDefaults.colors(
                thumbColor = Color.Transparent,
                activeTrackColor = Color.Transparent,
                inactiveTrackColor = Color.Transparent,
                activeTickColor = Color.Transparent,
                inactiveTickColor = Color.Transparent,
            ),
            steps = 10,
            valueRange = 0f..10f
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ReviewAndRatingPreview() {
    UX62550Theme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ScreenReviewAndRating(media = MediaDetailExample.MediaDetailObjectExample, navigateBack = {}, showSnackbar = {})
        }
    }
}
