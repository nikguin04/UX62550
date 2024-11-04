package com.niklas.ux62550

import HorizontalLazyRowWithSnapEffect
import MediaItemsViewModel
import android.os.Bundle
import android.provider.MediaStore.Audio.Media
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.niklas.ux62550.ui.theme.UX62550Theme
import com.niklas.ux62550.ScreenWithGeneralNavBar
import dk.shape.dtu.androidarchitecture.feature.fruitlist.MediaItemsList

/*class HomeScreen: ComponentActivity() {
    private val mediaItemsViewModel: MediaItemsViewModel by viewModels()

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

/*@Composable
@Preview(showBackground = true, name = "MainPreview")
fun HomePreview() {
    UX62550Theme (darkTheme = true, dynamicColor = false) {
        ScreenWithGeneralNavBar {
            ScreenHome()
        }
    }
}*/


@Composable
fun ScreenHome(modifier: Modifier = Modifier, mediaItemsViewModel: MediaItemsViewModel) {
    Column(modifier.padding()) {
        Row(
            modifier.fillMaxWidth().padding(FigmaPxToDp_w(29.5f), FigmaPxToDp_h(40f), 0.dp, FigmaPxToDp_h(35f)), // ConvertPxToDp(29.5f)
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            LogoBox(size= FigmaPxToDp_w(50f))

            Box (modifier = Modifier.padding(FigmaPxToDp_w(20f), 0.dp, 0.dp ,0.dp)) {

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
        val uiState = mediaItemsViewModel.mediaItemsState.collectAsState().value
        when (uiState) {
            MediaItemsUIState.Empty -> {
                Text(
                    text = "No MediasItems",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            is MediaItemsUIState.Data -> {
                HorizontalLazyRowWithSnapEffect(uiState.mediaItems)
            }
            else -> {

            }
        }

    }
}
