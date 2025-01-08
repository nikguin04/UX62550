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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.niklas.ux62550.ui.theme.AwardAndDetailRating

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailedRating(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(8.dp, 12.dp, 0.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Needs to be made to a button later on
        Text("Detailed Rating", color = AwardAndDetailRating)
        val sheetState = rememberModalBottomSheetState()
        var showBottomSheet by remember { mutableStateOf(false) }
        IconButton(
            onClick = {
                showBottomSheet = true
            },
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
                for (i in 1..4) {
                    Row(
                        modifier = Modifier.padding(50.dp, 0.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Category: $i")
                        Spacer(modifier = Modifier.width(12.dp))
                        Row {
                            var rating by remember { mutableIntStateOf(0) }
                            for (i in 1..5) {
                                val isFilled = i <= rating
                                val starIcon = if (isFilled) Icons.Outlined.Star else Icons.Outlined.StarOutline

                                Image(
                                    imageVector = starIcon,
                                    modifier = Modifier
                                        .requiredSize(18.dp),
                                    colorFilter = ColorFilter.tint(if (isFilled) Color.Yellow else Color.Gray), // Change color based on filled or not
                                    contentDescription = "Star icon"
                                )
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "$rating/5",
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
            }
        }
    }

}