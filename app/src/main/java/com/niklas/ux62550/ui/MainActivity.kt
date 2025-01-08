package com.niklas.ux62550.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.niklas.ux62550.navigation.GeneralNavBar
import com.niklas.ux62550.navigation.GeneralTopBar
import com.niklas.ux62550.navigation.MainNavHost
import com.niklas.ux62550.ui.theme.UX62550Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        // Enable fullscreen and edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                //android.graphics.Color.TRANSPARENT,
                Color(0.1f, 0.1f, 0.1f, 0.4f).toArgb()
            )
        )

        super.onCreate(savedInstanceState)

        setContent {
            UX62550Theme(darkTheme = true) {
                val navController = rememberNavController()
                var canNavigateBack by remember { mutableStateOf(false) }
                LaunchedEffect(navController.currentBackStackEntryAsState().value) {
                    canNavigateBack = navController.previousBackStackEntry != null
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { GeneralTopBar(navController, canNavigateBack) },
                    bottomBar = { GeneralNavBar(navController) },
                    contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
                ) { innerPadding ->
                    MainNavHost(
                        navController = navController,
                        onRouteChanged = {},
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }
