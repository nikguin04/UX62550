package com.niklas.ux62550.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material.icons.automirrored.outlined.ArrowLeft
import androidx.compose.material.icons.automirrored.outlined.StarHalf
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.ArrowLeft
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.PlayCircleOutline
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.niklas.ux62550.LogoBox
import com.niklas.ux62550.R
import com.niklas.ux62550.ScreenWithGeneralNavBar
import com.niklas.ux62550.features.MediaItemList.MediaItemsUIState
import com.niklas.ux62550.figmaPxToDp_h
import com.niklas.ux62550.figmaPxToDp_w
import com.niklas.ux62550.models.MediaItem
import com.niklas.ux62550.modules.HorizontalLazyRowWithSnapEffect
import com.niklas.ux62550.ui.theme.UX62550Theme

@Composable
@Preview(showBackground = true, name = "MediaDetailPagePreview")
fun MediaDetailPagePreview(){
    val mediaItems = listOf(
        MediaItem("Name 1", R.drawable.logo, Color.Blue),
        MediaItem("Name 2", R.drawable.logo, Color.Red)
    )

    UX62550Theme (darkTheme = true, dynamicColor = false) {
        ScreenWithGeneralNavBar {
            ScreenMediaDetail(mediaItemsUIState = MediaItemsUIState.Data(mediaItems))
        }
    }
}

@Composable
fun ScreenMediaDetail(modifier: Modifier = Modifier, mediaItemsUIState: MediaItemsUIState) {
    Column{
        Box(
            modifier.fillMaxWidth()
        ) {
            Box(Modifier.alpha(0.5f)) {
                Box(Modifier.background(Color.Red).fillMaxWidth().height(230.dp))
            }
            Box(
                modifier.fillMaxWidth().padding(30.dp).absolutePadding(0.dp,40.dp,4.dp,0.dp)
            ) {
                Box(Modifier.background(Color.Blue).fillMaxWidth().aspectRatio(16f / 9f))
                Image(
                    Icons.Outlined.PlayCircleOutline,
                    modifier = Modifier.align(Alignment.Center).absolutePadding(0.dp,0.dp,0.dp,40.dp)
                        .requiredSize(72.dp),
                    colorFilter = ColorFilter.tint(Color.White),
                    contentDescription = "Play circle"
                )
                TitleText()
            }
            Image(
                Icons.AutoMirrored.Outlined.ArrowBack,
                modifier = Modifier.padding(12.dp).requiredSize(36.dp),
                colorFilter = ColorFilter.tint(Color.White),
                contentDescription = "Arrow back"
            )
            Image(
                Icons.Outlined.BookmarkBorder,
                modifier = Modifier.align(Alignment.TopEnd).padding(12.dp).requiredSize(36.dp),
                colorFilter = ColorFilter.tint(Color.White),
                contentDescription = "Bookmark"
            )

            Row(
                modifier = Modifier.align(Alignment.BottomStart).padding(10.dp,0.dp,0.dp,0.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                repeat(5) {
                    Image(
                        imageVector = Icons.Outlined.StarOutline,
                        modifier = Modifier.requiredSize(18.dp),
                        colorFilter = ColorFilter.tint(Color.White),
                        contentDescription = "Star icon"
                    )
                }
                Text("0/5", fontSize = 18.sp) //Needs get a function for how many stars
                Text("Year",fontSize = 18.sp)
                Text("18+", fontSize = 18.sp)
            }

        }
        Row(
            modifier = Modifier.padding(4.dp,10.dp,0.dp,0.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text("PLACEHOLDER")
        }
        ResumeText()

        Text("Actors and Directors",  modifier.padding(4.dp,0.dp,0.dp,0.dp))
        Row {
            repeat(4) {
                DrawCircle(Modifier.size(64.dp))
            }
        }
        Row(
            modifier = Modifier.padding(4.dp,10.dp,0.dp,0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = Icons.Outlined.EmojiEvents,
                modifier = Modifier.requiredSize(18.dp),
                colorFilter = ColorFilter.tint(Color.White),
                contentDescription = "Star icon"
            )
            Text("Award...")
        }
        Row(
            modifier = Modifier.padding(4.dp,10.dp,0.dp,0.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            //Needs to be made to a button later on
            Text("Detailed Rating")
            Image(
                imageVector = Icons.AutoMirrored.Outlined.ArrowForwardIos,
                modifier = Modifier.requiredSize(12.dp),
                colorFilter = ColorFilter.tint(Color.White),
                contentDescription = "Star icon"
            )
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
fun TitleText() {
    Text("RED: The Movie",
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            shadow = Shadow(
                color = Color.Black, blurRadius = 10f
            ),
            textAlign = TextAlign.Center,
        ),
        modifier = Modifier.fillMaxWidth().padding(0.dp, 200.dp, 0.dp, 0.dp),
        )
}
@Composable
fun ResumeText(){
    Text("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat ",
        style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
        ),
        modifier = Modifier.fillMaxWidth().padding(4.dp, 0.dp, 0.dp, 0.dp),
    )
}
@Composable
fun DrawCircle(modifier: Modifier = Modifier) {
    androidx.compose.foundation.layout.Box(
        modifier = modifier.drawBehind {
            // Set the radius based on the smaller of the box dimensions
            val radius = size.minDimension / 2
            drawCircle(
                color = Color.Red,
                radius = radius,
                center = center
            )
        }
    )
}