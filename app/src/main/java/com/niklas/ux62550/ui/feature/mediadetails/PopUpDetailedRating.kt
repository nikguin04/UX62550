package com.niklas.ux62550.ui.feature.mediadetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material.icons.automirrored.outlined.StarHalf
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.niklas.ux62550.data.model.MovieDetailObject
import com.niklas.ux62550.ui.theme.ReviewColor

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DetailedRating(
    movieViewModel: MovieViewModel,
    movieID: MovieState.Data,
    onNavigateToReview: (MovieDetailObject) -> Unit,
    modifier: Modifier = Modifier
) {
    val movieReviewID by movieViewModel.movieReviewID.collectAsState()

    // Help from chat
    LaunchedEffect(movieID) {
        movieViewModel.getDetailReviews(movieID.mediaDetailObject.id)
    }
    Column(modifier.padding(20.dp, 25.dp, 20.dp, 5.dp)) {
        Text(
            "User Rating",
            style = TextStyle(
                fontSize = 20.sp,
                color = Color.White,
                shadow = Shadow(blurRadius = 5f)
            ),
            textAlign = TextAlign.Center
        )
        OverallRating(movieReviewID["MainRating"] ?: 0.0, modifier)
        ReviewButton(movieID, onNavigateToReview)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "Detailed Rating",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.White,
                    shadow = Shadow(blurRadius = 5f)
                )
            )
            val sheetState = rememberModalBottomSheetState()
            var showBottomSheet by remember { mutableStateOf(false) }
            IconButton(
                onClick = { showBottomSheet = true },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowForwardIos,
                    contentDescription = "Show bottom sheet",
                    tint = Color.White
                )
            }

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = sheetState
                ) {
                    Column {
                        DetailRatingRow("Acting", movieReviewID["Acting"] ?: 0.0)
                        DetailRatingRow("Directing", movieReviewID["Directing"] ?: 0.0)
                        DetailRatingRow("Plot", movieReviewID["Plot"] ?: 0.0)
                        DetailRatingRow("Music", movieReviewID["Music"] ?: 0.0)
                    }
                }
            }
        }
    }
}

@Composable
fun OverallRating(rating: Double, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..5) {
            val starIcon = when {
                i <= rating -> Icons.Outlined.Star
                i <= rating + 0.5 -> Icons.AutoMirrored.Outlined.StarHalf
                else -> Icons.Outlined.StarOutline
            }
            Image(
                imageVector = starIcon,
                modifier = Modifier
                    .requiredSize(18.dp)
                    .shadow(elevation = 15.dp),
                colorFilter = ColorFilter.tint(Color.Yellow),
                contentDescription = "Rating star"
            )
        }
        Spacer(Modifier.width(4.dp))
        Text(
            text = "$rating/5",
            fontSize = 18.sp
        )
    }
}

@Composable
fun DetailRatingRow(detailRatingName: String, rating: Double) {
    Row(
        modifier = Modifier
            .padding(horizontal = 30.dp, vertical = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Category: $detailRatingName",
            modifier = Modifier.weight(1f),
            fontSize = 16.sp
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 1..5) {
                val isFilled = i <= rating
                val starIcon = if (isFilled) Icons.Outlined.Star else Icons.Outlined.StarOutline
                Image(
                    imageVector = starIcon,
                    modifier = Modifier.requiredSize(18.dp),
                    colorFilter = ColorFilter.tint(
                        if (isFilled) Color.Yellow else Color.Gray
                    ),
                    contentDescription = "Star icon"
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "$rating/5",
                fontSize = 16.sp
            )
        }
    }
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        color = Color.Gray
    )
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
fun ReviewButton(movieState: MovieState.Data, onNavigateToReview: (MovieDetailObject) -> Unit) {
    Button(
        onClick = { onNavigateToReview(movieState.mediaDetailObject) },
        colors = ButtonColors(
            contentColor = ReviewColor,
            containerColor = ReviewColor,
            disabledContentColor = ReviewColor,
            disabledContainerColor = ReviewColor,
        ),
    ) {
        Text(
            "Give Rating",
            color = Color.White,
            style = TextStyle(
                shadow = Shadow(blurRadius = 10f)
            )
        )
    }
}
