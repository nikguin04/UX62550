package com.niklas.ux62550.ui.feature.popup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.niklas.ux62550.ui.theme.AwardAndDetailRating

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AwardPopUp() {
    Row(
        modifier = Modifier.padding(4.dp, 10.dp, 0.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = Icons.Outlined.EmojiEvents,
            modifier = Modifier.size(18.dp),
            colorFilter = ColorFilter.tint(Color.Yellow),
            contentDescription = "Star icon"
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text("Awards...", color = AwardAndDetailRating)
        val sheetState = rememberModalBottomSheetState()
        var showBottomSheet by remember { mutableStateOf(false) }

        // IconButton to trigger the Bottom Sheet
        IconButton(
            onClick = { showBottomSheet = true },
            modifier = Modifier.size(40.dp) // Set size directly on the IconButton if needed
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowForwardIos,
                contentDescription = "Show bottom sheet",
                tint = Color.White // Adjust color if necessary
            )
        }

        // Show the bottom sheet
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState
            ) {
                for (i in 0..2) {
                    Row(
                        modifier = Modifier.padding(50.dp, 0.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Emmy ${2020 + i}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    HorizontalDivider(
                        modifier = Modifier
                            .width(300.dp)
                            .padding(50.dp, 0.dp),
                        thickness = 0.5.dp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}
