package com.niklas.ux62550.ui.feature.mediadetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailedRating(
    modifier: Modifier = Modifier,
    movieViewModel: MovieViewModel,
    movieID: MovieState.Data
) {
    val movieReviewID by movieViewModel.movieReviewID.collectAsState()

    //Help from chat
    LaunchedEffect(movieID) {
        movieViewModel.getDetailReviews(movieID.mediaDetailObject.id)
    }

    Row(
        modifier = modifier.padding(20.dp, 10.dp, 20.dp, 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Detailed Rating",
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.White,
                shadow = Shadow(color = Color.Black, blurRadius = 5.0f)
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
                Row(
                    modifier = Modifier.padding(50.dp, 0.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Category: Acting")
                    Spacer(modifier = Modifier.width(12.dp))
                    Row {
                        val actorRating = movieReviewID["Acting"] ?: 0.0
                        for (i in 1..5) {
                            val isFilled = i <= actorRating
                            val starIcon =
                                if (isFilled) Icons.Outlined.Star else Icons.Outlined.StarOutline
                            Image(
                                imageVector = starIcon,
                                modifier = Modifier.requiredSize(18.dp),
                                colorFilter = ColorFilter.tint(
                                    if (isFilled) Color.Yellow else Color.Gray
                                ),
                                contentDescription = "Star icon"
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$actorRating/5",
                            fontSize = 18.sp,
                            modifier = Modifier.weight(0.5f)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp, 5.dp),
                    color = Color.Gray
                )
                Row(
                    modifier = Modifier.padding(50.dp, 0.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Category: Directing")
                    Spacer(modifier = Modifier.width(12.dp))
                    Row {
                        val actorRating = movieReviewID["Directing"] ?: 0.0
                        for (i in 1..5) {
                            val isFilled = i <= actorRating
                            val starIcon =
                                if (isFilled) Icons.Outlined.Star else Icons.Outlined.StarOutline
                            Image(
                                imageVector = starIcon,
                                modifier = Modifier.requiredSize(18.dp),
                                colorFilter = ColorFilter.tint(
                                    if (isFilled) Color.Yellow else Color.Gray
                                ),
                                contentDescription = "Star icon"
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$actorRating/5",
                            fontSize = 18.sp,
                            modifier = Modifier.weight(0.5f)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp, 5.dp),
                    color = Color.Gray
                )
                Row(
                    modifier = Modifier.padding(50.dp, 0.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Category: Music")
                    Spacer(modifier = Modifier.width(12.dp))
                    Row {
                        val actorRating = movieReviewID["Music"] ?: 0.0
                        for (i in 1..5) {
                            val isFilled = i <= actorRating
                            val starIcon =
                                if (isFilled) Icons.Outlined.Star else Icons.Outlined.StarOutline
                            Image(
                                imageVector = starIcon,
                                modifier = Modifier.requiredSize(18.dp),
                                colorFilter = ColorFilter.tint(
                                    if (isFilled) Color.Yellow else Color.Gray
                                ),
                                contentDescription = "Star icon"
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$actorRating/5",
                            fontSize = 18.sp,
                            modifier = Modifier.weight(0.5f)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp, 5.dp),
                    color = Color.Gray
                )
                Row(
                    modifier = Modifier.padding(50.dp, 0.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Category: Plot")
                    Spacer(modifier = Modifier.width(12.dp))
                    Row {
                        val actorRating = movieReviewID["Plot"] ?: 0.0
                        for (i in 1..5) {
                            val isFilled = i <= actorRating
                            val starIcon =
                                if (isFilled) Icons.Outlined.Star else Icons.Outlined.StarOutline
                            Image(
                                imageVector = starIcon,
                                modifier = Modifier.requiredSize(18.dp),
                                colorFilter = ColorFilter.tint(
                                    if (isFilled) Color.Yellow else Color.Gray
                                ),
                                contentDescription = "Star icon"
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$actorRating/5",
                            fontSize = 18.sp,
                            modifier = Modifier.weight(0.5f)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp, 5.dp),
                    color = Color.Gray
                )



            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
