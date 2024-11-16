package com.niklas.ux62550.ui.screen.popup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.niklas.ux62550.models.Movie
import com.niklas.ux62550.navigation.ScreenWithGeneralNavBar
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
    val navController = rememberNavController()
    UX62550Theme (darkTheme = true, dynamicColor = false) {
        ScreenWithGeneralNavBar(navController) {
            PopupAward(movie = movie)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopupAward(movie: Movie) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    Scaffold( // TODO FAT CHRISTIAN
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Show bottom sheet") },
                icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                onClick = {
                    showBottomSheet = true
                }
            )
        }
    ) { contentPadding -> // Now we use contentPadding here.
        Box(modifier = Modifier.padding(contentPadding)) {
            Text("Main content goes here.")
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
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
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}
