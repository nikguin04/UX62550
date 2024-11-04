package com.niklas.ux62550.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
    Column {
        Box(
            modifier.fillMaxWidth()
        ) {
            Box(Modifier.alpha(0.5f)) {
                Box(Modifier.background(Color.Red).fillMaxWidth().height(230.dp))
            }
            Box(
                modifier.fillMaxWidth().padding(30.dp).absolutePadding(0.dp,50.dp,0.dp,0.dp)
            ) {
                Box(Modifier.background(Color.Yellow).fillMaxWidth().aspectRatio(16f / 9f))

            }
            Icon(
                Icons.Outlined.BookmarkBorder,
                contentDescription = "Bookmark"
            )


        }


        // Implement viewmodel
        //val uiState = mediaItemsViewModel.mediaItemsState.collectAsState().value
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