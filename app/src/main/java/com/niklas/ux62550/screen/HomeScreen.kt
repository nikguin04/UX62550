package com.niklas.ux62550.screen

import androidx.activity.viewModels
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import com.niklas.ux62550.features.MediaItemList.MediaItemsUIState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.niklas.ux62550.R
import com.niklas.ux62550.ScreenWithGeneralNavBar
import com.niklas.ux62550.figmaPxToDp_h
import com.niklas.ux62550.figmaPxToDp_w
import com.niklas.ux62550.models.MediaItem
import com.niklas.ux62550.modules.HorizontalLazyRowWithSnapEffect
import com.niklas.ux62550.ui.theme.UX62550Theme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.android.libraries.mapsplatform.transportation.consumer.model.Route
import com.niklas.ux62550.features.MediaItemList.MediaItemsViewModel
import com.niklas.ux62550.modules.HorizontalLazyRowMoviesWithCat

/*class HomeScreen: ComponentActivity() {
    private val mediaItemsViewModel: com.niklas.ux62550.features.MediaItemList.MediaItemsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UX62550Theme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    UX62550Theme (darkTheme = true, dynamicColor = false) {
                        Surface(
                            modifier = Modifier.fillMaxSize().padding((innerPadding))
                            //color = Color_background,
                        ) {

                            ScreenWithGeneralNavBar() {
                                ScreenHome(mediaItemsViewModel = mediaItemsViewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}*/

@Composable
@Preview(showBackground = true, name = "MainPreview")
fun HomePreview() {
    val mediaItems = listOf(
        MediaItem("Name 1", R.drawable.logo, Color.Blue),
        MediaItem("Name 2", R.drawable.logo, Color.Red),
        MediaItem("Name 3", R.drawable.logo, Color.Yellow)
    )

    UX62550Theme (darkTheme = true, dynamicColor = false) {
        ScreenWithGeneralNavBar {
            HomeScreen(onNavigateToMediaDeatilsScreen = {})
        }
    }
}

@Composable
fun HomeScreen(
    onNavigateToMediaDeatilsScreen: (String) -> Unit,
    mediaItemsViewModel: MediaItemsViewModel = viewModel()
) {

    //val mediaItemsViewModel: MediaItemsViewModel by viewModels()
    val uiState = mediaItemsViewModel.mediaItemsState.collectAsState().value
    Surface(
        modifier = Modifier.fillMaxSize()
        //color = Color_background,
    ) {


        ScreenWithGeneralNavBar() {
            // WARNING, TEMPORARY BUTTON
            Button(
                onClick = {
                    onNavigateToMediaDeatilsScreen("Hello123")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )
            ) {
                Text(text = "Go to media details page")
            }
            // WARNING, TEMPORARY BUTTON

            ScreenHome(mediaItemsUIState = uiState)
        }
    }
}


@Composable
fun ScreenHome(modifier: Modifier = Modifier, mediaItemsUIState: MediaItemsUIState) {

    Column(modifier.padding()) {
        Row(
            modifier.fillMaxWidth().padding(figmaPxToDp_w(29.5f), figmaPxToDp_h(40f), 0.dp, figmaPxToDp_h(35f)), // ConvertPxToDp(29.5f)
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            LogoBox(size= figmaPxToDp_w(50f))

            Box (modifier = Modifier.padding(figmaPxToDp_w(20f), 0.dp, 0.dp ,0.dp)) {

                Text(
                    text = "Welcome, User1",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Normal,
                    ),
                )

            }
        }


        // Implement viewmodel
        //val uiState = mediaItemsViewModel.mediaItemsState.collectAsState().value
        when (mediaItemsUIState) {
            MediaItemsUIState.Empty -> {
                Text(
                    text = "No Media Items",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            is MediaItemsUIState.Data -> {
                HorizontalLazyRowWithSnapEffect(mediaItemsUIState.mediaItems)

                HorizontalDotIndexer(Modifier.size(LocalConfiguration.current.screenWidthDp.dp, 12.dp))
            }
            else -> {

            }
        }

        val cats = listOf("Romance", "Action", "Comedy", "Drama")

        for (cat in cats) {
            // TODO: PLEASE READ! this is a placeholder for when we get a proper structure for fetching movies
            when (mediaItemsUIState) {
                MediaItemsUIState.Empty -> {
                    Text(
                        text = "No Media Items",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                is MediaItemsUIState.Data -> {
                    Row(
                        Modifier.padding(
                            figmaPxToDp_w(13f),
                            figmaPxToDp_w(10f),
                            0.dp,
                            figmaPxToDp_w(2f)
                        )
                    ) { Text(text = cat) }
                    HorizontalLazyRowMoviesWithCat(
                        Modifier.padding(0.dp, 0.dp),
                        155f,
                        87.19f,
                        mediaItemsUIState.mediaItems
                    )
                }

                else -> {

                }
            }
        }

    }
}

@Composable
fun HorizontalDotIndexer(modifier: Modifier) {
    //Icons.AutoMirrored.Filled.Dot
    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0..4) {
            Icon(
                Icons.Filled.Circle,
                contentDescription = "test123",
                modifier = Modifier.padding(1.5.dp,0.dp,1.5.dp,0.dp)
            )
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