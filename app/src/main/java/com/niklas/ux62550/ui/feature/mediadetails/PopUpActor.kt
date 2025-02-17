package com.niklas.ux62550.ui.feature.mediadetails

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.niklas.ux62550.data.model.Cast
import com.niklas.ux62550.data.model.CreditObject
import com.niklas.ux62550.data.model.Crew
import com.niklas.ux62550.ui.feature.common.CreditState
import com.niklas.ux62550.ui.feature.common.DrawCircle
import com.niklas.ux62550.ui.feature.common.ImageSize
import com.niklas.ux62550.ui.feature.common.MediaItem

// Used this website for BottomSheets
// https://www.geeksforgeeks.org/android-bottomsheet-example-in-kotlin/
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ActorsAndDirectors(creditState: CreditState.Data, modifier: Modifier = Modifier) {
    // ChatGPT has been used in the following to figure out how to
    // get the screen width and adjust the amount of actors shown
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val actorItemWidth = 60.dp + 12.dp
    val maxActors = ((screenWidth - 80.dp) / actorItemWidth).toInt()

    Column(Modifier.padding(horizontal = 20.dp)) {
        Text(
            "Actors and Directors",
            Modifier.padding(top = 15.dp),
            style = TextStyle(
                fontSize = 20.sp,
                color = Color.White,
                shadow = Shadow(blurRadius = 5f)
            )
        )
        val sheetState = rememberModalBottomSheetState()
        var showBottomSheet by remember { mutableStateOf(false) }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .clickable { showBottomSheet = true },
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 0 until minOf(maxActors, creditState.creditObject.cast.size)) {
                MediaItem(
                    uri = creditState.creditObject.cast[i].profilePath,
                    size = ImageSize.PROFILE,
                    modifier = modifier
                        .clip(RoundedCornerShape(25))
                        .size(60.dp, 90.dp)
                )
                Spacer(Modifier.width(12.dp))
            }

            repeat(3) { i ->
                if (i > 0) { Spacer(Modifier.width(4.dp)) }
                DrawCircle(Color.LightGray, Modifier.size(10.dp))
            }
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
                        CastRow(cast)
                    }
                    items(creditState.creditObject.crew) { crew ->
                        CrewRow(crew)
                    }
                }
            }
        }
    }
}

@Composable
fun CastRow(cast: Cast, modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        MediaItem(
            uri = cast.profilePath,
            size = ImageSize.PROFILE,
            modifier = modifier
                .clip(RoundedCornerShape(25))
                .size(60.dp, 90.dp)
        )
        Spacer(Modifier.width(12.dp))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = cast.name,
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(Modifier.width(4.dp))
                DrawCircle(Color.LightGray, Modifier.size(6.dp))
                Spacer(Modifier.width(4.dp))
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
fun CrewRow(crew: Crew, modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        MediaItem(
            uri = crew.profilePath,
            size = ImageSize.PROFILE,
            modifier = modifier
                .clip(RoundedCornerShape(25))
                .size(60.dp, 90.dp)
        )
        Spacer(Modifier.width(12.dp))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = crew.name,
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(Modifier.width(4.dp))
                DrawCircle(Color.LightGray, Modifier.size(6.dp))
                Spacer(Modifier.width(4.dp))
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

@Composable
@Preview
fun PreviewActorsAndDirectors() {
    val creditState = CreditState.Data(
        creditObject = CreditObject(
            cast = listOf(
                Cast(name = "Dwayne Johnson", profilePath = "/5QApZVV8FUFlVxQpIK3Ew6cqotq.jpg", character = "Character 1")
            ),
            crew = listOf()
        )
    )
    ActorsAndDirectors(creditState = creditState)
}
