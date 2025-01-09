package com.niklas.ux62550.ui.feature.mediadetails

import DrawCircle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.niklas.ux62550.data.model.Cast
import com.niklas.ux62550.data.model.Crew
import com.niklas.ux62550.ui.feature.common.CreditState
import com.niklas.ux62550.ui.feature.common.ImageSize
import com.niklas.ux62550.ui.feature.common.MediaItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActorsAndDirectors(modifier: Modifier = Modifier, creditState: CreditState.Data) {
    Text("Actors and Directors", Modifier.padding(8.dp, 12.dp, 0.dp, 0.dp))
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    Row(
        modifier = modifier.fillMaxWidth()
            .padding(8.dp, 0.dp, 0.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until minOf(4, creditState.creditObject.cast.size)) {
            MediaItem(
                creditState.creditObject.cast[i].castProfilePath,
                0.dp,
                modifier
                    .clip(RoundedCornerShape(25))
                    .size(width = 60.dp, height = 90.dp),
                size = ImageSize.PROFILE)
            Spacer(modifier = Modifier.width((12.dp)))
        }

        for (i in 0..2) { // Create clickable circles
            Spacer(modifier = Modifier.width(2.dp))
            IconButton(
                onClick = {
                    showBottomSheet = true
                },
                modifier = Modifier.size(12.dp) // Size of the clickable button area
            ) {
                DrawCircle(Modifier.size(10.dp), Color.LightGray) // Circle inside the button
            }
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = sheetState
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(creditState.creditObject.cast) { cast ->
                            CastRow(modifier, cast)
                        }
                        items(creditState.creditObject.crew) { crew ->
                            CrewRow(modifier, crew)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CastRow(modifier: Modifier, cast: Cast){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        MediaItem(cast.castProfilePath,
            0.dp,
            modifier
                .clip(RoundedCornerShape(25))
                .size(width = 60.dp, height = 90.dp),
            size = ImageSize.PROFILE)
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = cast.castName,
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(4.dp))
                DrawCircle(Modifier.size(6.dp), Color.LightGray)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = cast.character,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

        }
    }
}
@Composable
fun CrewRow(modifier: Modifier, crew: Crew){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        MediaItem(crew.castProfilePath,
            0.dp,
            modifier
                .clip(RoundedCornerShape(25))
                .size(width = 60.dp, height = 90.dp),
            size = ImageSize.PROFILE
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = crew.castName,
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(4.dp))
                DrawCircle(Modifier.size(6.dp), Color.LightGray)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = crew.job,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

        }
    }
}