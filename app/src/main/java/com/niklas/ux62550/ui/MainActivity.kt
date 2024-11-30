package com.niklas.ux62550.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.niklas.ux62550.navigation.GeneralNavBar
import com.niklas.ux62550.navigation.MainNavHost
import com.niklas.ux62550.ui.theme.UX62550Theme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UX62550Theme(darkTheme = true) {
                val navController = rememberNavController()
                var canNavigateBack by remember { mutableStateOf(false) }
                var currentScreenTitle by remember { mutableStateOf("") }
                LaunchedEffect(navController.currentBackStackEntryAsState().value) {
                    canNavigateBack = navController.previousBackStackEntry != null
                }

                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        // Moved down to content for padding reasons?
                        // TODO: This seems really scuffed and *NOT* like the way to do things
                    },
                    bottomBar = { GeneralNavBar(navController) }
                ) {
                    Box {
                        MainNavHost(
                            navController = navController,
                            onRouteChanged = { route -> currentScreenTitle = route.title },
                            modifier = Modifier.padding(it)
                        )
                        TopAppBar(
                            title = {},
                            navigationIcon = {
                                if (canNavigateBack) {
                                    IconButton(onClick = { navController.popBackStack() }) {
                                        // Shadow icon (weird workaround) TODO: Should make a composable for this stupid shit so we can actually use m3 icons like in figma
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .offset(x = 0.dp, y = 2.dp), // Offset to create shadow appearance
                                            tint = Color.Black.copy(alpha = 0.3f) // Shadow color and transparency
                                        )
                                        // Actual icon :))))
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Back"
                                        )
                                    }
                                }
                            },
                            colors = TopAppBarColors(
                                containerColor = Color(0x00000000),
                                scrolledContainerColor = TopAppBarDefaults.topAppBarColors().scrolledContainerColor,
                                navigationIconContentColor = TopAppBarDefaults.topAppBarColors().navigationIconContentColor,
                                titleContentColor = TopAppBarDefaults.topAppBarColors().titleContentColor,
                                actionIconContentColor = TopAppBarDefaults.topAppBarColors().actionIconContentColor
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }
