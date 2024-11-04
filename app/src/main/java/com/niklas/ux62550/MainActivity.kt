package com.niklas.ux62550

import com.niklas.ux62550.features.MediaItemList.MediaItemsUIState
import com.niklas.ux62550.features.MediaItemList.MediaItemsViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.niklas.ux62550.ui.theme.UX62550Theme

class MainActivity : ComponentActivity() {
    private val mediaItemsViewModel: MediaItemsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UX62550Theme {
                val uiState = mediaItemsViewModel.mediaItemsState.collectAsState().value

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding),//.background(Color_background)
                        mediaItemsUIState = uiState
                    )
                }
            }
        }
    }
}



@Composable
fun ScreenImage() {
    //val image = painterResource(R.drawable.ic_launcher_background)

    BoxWithRoundedImage()
}

@Composable
fun BoxWithRoundedImage() {
    // Image size, for example, 200dp
    val imageSize = 200.dp

    // Compose Box with the image and 5% rounded corners
    Box(
        modifier = Modifier.size(imageSize).clip(RoundedCornerShape(5))
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background), // Your image resource
            contentDescription = null,
            modifier = Modifier
                .size(imageSize) // Same size for the image
                .clip(RoundedCornerShape(0.05f * imageSize.value)), // Apply 5% rounded corners dynamically
            contentScale = ContentScale.Crop // Crops the image to fit the box
        )
    }
}

@Composable
fun PromoItem(width: Dp, height: Dp, round: Dp, color: Color, modifier: Modifier = Modifier) {

        Box(
            modifier = modifier.clip(RoundedCornerShape(round)).background(color).size(width, height).padding()

        )
}

@Composable
fun LogoBox(size: Dp) {
    // Image size, for example, 200dp
    val imageSize = size

    // Compose Box with the image and 5% rounded corners
    Box(
        modifier = Modifier.size(imageSize).clip(RoundedCornerShape(5))
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo), // Your image resource
            contentDescription = null,
            modifier = Modifier
                .size(imageSize) // Same size for the image
                .clip(RoundedCornerShape(0.00f * imageSize.value)),

            contentScale = ContentScale.Crop // Crops the image to fit the box
        )
    }
}



/*@Preview(showBackground = true, name = "MainPreview")
@Composable
fun Preview() {
    MainScreen()
}*/

@Composable
fun MainScreen(modifier: Modifier = Modifier, mediaItemsUIState: MediaItemsUIState) {

    UX62550Theme (darkTheme = true, dynamicColor = false) {
        Surface(
            modifier = Modifier.fillMaxSize()
            //color = Color_background,
        ) {

            ScreenWithGeneralNavBar() {
                ScreenHome(mediaItemsUIState = mediaItemsUIState)
            }
        }
    }
}



@Composable
fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }


