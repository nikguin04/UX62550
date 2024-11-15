package com.niklas.ux62550.ui.screen_mediadetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.PlayCircleOutline
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.niklas.ux62550.R
import com.niklas.ux62550.models.MediaItem
import com.niklas.ux62550.models.Movie
import com.niklas.ux62550.navigation.ScreenWithGeneralNavBar
import com.niklas.ux62550.ui.screen_home.HorizontalLazyRowWithSnapEffect
import com.niklas.ux62550.ui.screen_home.MediaItemsUIState
import com.niklas.ux62550.ui.theme.AwardAndDetailRating
import com.niklas.ux62550.ui.theme.DescriptionColor
import com.niklas.ux62550.ui.theme.UX62550Theme
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes

@Composable
@Preview(showBackground = true, name = "MediaDetailPagePreview")
fun MediaDetailPagePreview(){
    val movie = Movie("RED: The Movie",
            "2090",
            3000.minutes,
            3.5,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat ",
            listOf("Action", "Dinosaur Adventure", "Hentai"),
            13
            )

    val mediaItems = listOf(
        MediaItem("Name 1", R.drawable.logo, Color.Blue),
        MediaItem("Name 2", R.drawable.logo, Color.Red)
    )
    val navController = rememberNavController()
    UX62550Theme (darkTheme = true, dynamicColor = false) {
        ScreenWithGeneralNavBar(navController) {
            ScreenMediaDetail(mediaItemsUIState = MediaItemsUIState.Data(mediaItems), movie = movie)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenMediaDetail(modifier: Modifier = Modifier, mediaItemsUIState: MediaItemsUIState, movie: Movie) {

    Column{
        Box(
            modifier.fillMaxWidth()
        ) {
            Box(Modifier.alpha(0.5f)) {
                Box(
                    Modifier
                        .background(Color.Red)
                        .fillMaxWidth()
                        .height(230.dp))
            }
            Box(
                modifier
                    .fillMaxWidth()
                    .padding(30.dp)
                    .absolutePadding(0.dp, 40.dp, 4.dp, 0.dp)
            ) {
                Box(
                    Modifier
                        .background(Color.Blue)
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f))
                Image(
                    Icons.Outlined.PlayCircleOutline,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .absolutePadding(0.dp, 0.dp, 0.dp, 40.dp)
                        .requiredSize(72.dp),
                    colorFilter = ColorFilter.tint(Color.White),
                    contentDescription = "Play circle"
                )
                TitleText(movie.name)
            }
            Image(
                Icons.AutoMirrored.Outlined.ArrowBack,
                modifier = Modifier
                    .padding(12.dp)
                    .requiredSize(36.dp),
                colorFilter = ColorFilter.tint(Color.White),
                contentDescription = "Arrow back"
            )
            Image(
                Icons.Outlined.BookmarkBorder,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .requiredSize(36.dp),
                colorFilter = ColorFilter.tint(Color.White),
                contentDescription = "Bookmark"
            )

            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(4.dp, 0.dp, 0.dp, 0.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                for(i in 0..4) {
                    Image( //Needs to be made button
                        imageVector = Icons.Outlined.StarOutline,
                        modifier = Modifier.requiredSize(18.dp),
                        colorFilter = ColorFilter.tint(Color.Yellow),
                        contentDescription = "Star icon"
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(  //Needs get a function for how many stars
                    movie.rating.toString(),
                    fontSize = 18.sp,
                    modifier = Modifier.weight(0.5f)
                )
                Text(
                    movie.year,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(0.5f)
                )
                Text(
                    movie.duration.toString(),
                    fontSize = 18.sp,
                    modifier = Modifier.weight(0.5f)
                )
                Text(
                    movie.pgRating.toString() + "+",
                    fontSize = 18.sp,
                    modifier = Modifier.weight(0.3f)
                )
            }

        }
        Row(
            modifier = Modifier
                .padding(4.dp, 10.dp, 0.dp, 0.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            for ((index, genre) in movie.genres.withIndex()) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(40.dp))
                        .background(Color.Gray)
                        .padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        )
                )
                {
                    Text(
                        text = genre,
                        fontSize = 12.sp,
                        fontWeight = Bold,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                if (index != movie.genres.lastIndex) {
                    Spacer(modifier = Modifier.width(4.dp))
                    DrawLittleCircle(Modifier.size(10.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
            Spacer(modifier = Modifier.weight(0.3f))
            repeat(3) { // Needs to be changed to Where to Watch
                Spacer(modifier = Modifier.width(4.dp))
                DrawLittleCircle(Modifier.size(10.dp))
            }
            Spacer(modifier = Modifier.weight(0.05f))
        }
        DescriptionText(movie.description)

        Text("Actors and Directors",  modifier.padding(4.dp,2.dp,0.dp,0.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(4) {
                Spacer(modifier = Modifier.width(4.dp))
                DrawCircle(Modifier.size(64.dp), Color.Red)
            }
            Spacer(modifier = Modifier.width(4.dp))
            repeat(3) { //Needs to be made button
                Spacer(modifier = Modifier.width(4.dp))
                DrawLittleCircle(Modifier.size(10.dp))

            }
        }
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
            Text("Award...", color = AwardAndDetailRating)
            val sheetState = rememberModalBottomSheetState()
            var showBottomSheet by remember { mutableStateOf(false) }


                // IconButton to trigger the Bottom Sheet
                IconButton(
                    onClick = {
                        showBottomSheet = true
                    },
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
                            Row (
                                modifier = Modifier.padding(50.dp,0.dp),
                                verticalAlignment = Alignment.CenterVertically

                            )
                            {
                                Text("Emmy ${2020 + i}",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            HorizontalDivider(
                                modifier = Modifier.width(300.dp).
                                padding(50.dp,0.dp),
                                thickness = 0.5.dp,
                                color = Color.Gray)

                        }


                }
            }
        }

        Row(
            modifier = Modifier.padding(4.dp,10.dp,0.dp,0.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            //Needs to be made to a button later on
            Text("Detailed Rating", color = AwardAndDetailRating)
            val sheetState = rememberModalBottomSheetState()
            var showBottomSheet by remember { mutableStateOf(false) }
            IconButton(
                onClick = {
                    showBottomSheet = true
                },
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
                    for (i in 1..4) {
                        Row (
                            modifier = Modifier.padding(50.dp,0.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp), // Space between stars
                            verticalAlignment = Alignment.CenterVertically
                        )
                        {

                            Text("Category"+ i, )
                            Spacer(modifier = Modifier.width(12.dp))
                            Row () {
                                for(j in 0..4)
                                Image(
                                    imageVector = Icons.Outlined.StarOutline,
                                    modifier = Modifier.requiredSize(18.dp),
                                    colorFilter = ColorFilter.tint(Color.Yellow),
                                    contentDescription = "Star icon"
                                )


                                HorizontalDivider(
                                    modifier = Modifier.width(130.dp).
                                    padding(0.dp,0.dp),
                                    thickness = 0.5.dp,
                                    color = Color.Gray
                                )

                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(

                            modifier = Modifier.fillMaxWidth().
                            padding(110.dp,0.dp),
                            thickness = 0.5.dp,
                            color = Color.Gray)
                }
            }
        }
       }


        // Implement viewmodel
        //val uiState = mediaItemsViewModel.mediaItemsState.collectAsState().value
        Text("Movies similar to this one", modifier.padding(4.dp,0.dp,0.dp,0.dp))
        when (mediaItemsUIState) {
            MediaItemsUIState.Empty -> {
                Text(
                    text = "No MediasItems",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            is MediaItemsUIState.Data -> {
                HorizontalLazyRowWithSnapEffect(mediaItemsUIState.mediaItems)
            }
            else -> {

            }
        }

    }
}
@Composable
fun TitleText(movieTitle: String) {
    Text(movieTitle,
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            shadow = Shadow(
                color = Color.Black, blurRadius = 10f
            ),
            textAlign = TextAlign.Center,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 200.dp, 0.dp, 0.dp),
        )
}
@Composable
fun DescriptionText(movieDescription: String){
    Text(movieDescription,
        style = TextStyle(
            lineHeight = 1.25.em,
            lineBreak = LineBreak.Paragraph,
            fontSize = 14.sp,
            textAlign = TextAlign.Start,
            color = DescriptionColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp, 0.dp, 0.dp, 0.dp),
    )
}
@Composable
fun DrawCircle(modifier: Modifier = Modifier, color: Color) {
    androidx.compose.foundation.layout.Box(
        modifier = modifier.drawBehind{
            // Set the radius based on the smaller of the box dimensions
            val radius = size.minDimension / 2
            drawCircle(
                color = color,
                radius = radius,
                center = center
            )
        }
    )
}
@Composable
fun DrawLittleCircle(modifier: Modifier = Modifier) {
    androidx.compose.foundation.layout.Box(
        modifier = modifier.drawBehind{
            // Set the radius based on the smaller of the box dimensions
            val radius = size.minDimension / 2
            drawCircle(
                color = Color.LightGray,
                radius = radius,
                center = center
            )
        }
    )
}
