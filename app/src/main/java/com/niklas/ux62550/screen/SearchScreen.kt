package com.niklas.ux62550.screen
import com.niklas.ux62550.features.MediaItemList.MediaItemsUIState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.niklas.ux62550.LogoBox
import com.niklas.ux62550.R
import com.niklas.ux62550.ScreenWithGeneralNavBar
import com.niklas.ux62550.figmaPxToDp_h
import com.niklas.ux62550.figmaPxToDp_w
import com.niklas.ux62550.models.MediaItem
import com.niklas.ux62550.modules.HorizontalLazyRowWithSnapEffect
import com.niklas.ux62550.ui.theme.UX62550Theme

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
@Preview(showBackground = true, name = "SearchPreview")
fun SearchPreview() {
    val mediaItems = listOf(
        MediaItem("Name 1", R.drawable.logo, Color.Blue),
        MediaItem("Name 2", R.drawable.logo, Color.Red)
    )

    UX62550Theme (darkTheme = true, dynamicColor = false) {
        ScreenWithGeneralNavBar {
            ScreenSearch(mediaItemsUIState = MediaItemsUIState.Data(mediaItems))
        }
    }
}


@Composable
fun ScreenSearch(modifier: Modifier = Modifier, mediaItemsUIState: MediaItemsUIState) {
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
