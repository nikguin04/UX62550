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
import com.niklas.ux62550.screen.ScreenHome
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


