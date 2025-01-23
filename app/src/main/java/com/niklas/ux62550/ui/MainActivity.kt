package com.niklas.ux62550.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.niklas.ux62550.BuildConfig
import com.niklas.ux62550.di.DataModule
import com.niklas.ux62550.navigation.GeneralNavBar
import com.niklas.ux62550.navigation.MainNavHost
import com.niklas.ux62550.ui.theme.UX62550Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        // Enable fullscreen and edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)

        DataModule.initialize()
        super.onCreate(savedInstanceState)

        setContent {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.dark(
                    //android.graphics.Color.TRANSPARENT,
                    Color(0.1f, 0.1f, 0.1f, 0.4f).toArgb()
                ),
                navigationBarStyle = SystemBarStyle.dark(
                    Color.Transparent.toArgb()
                )
            )

            UX62550Theme {
                val navController = rememberNavController()

                val snackbarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()
                val snackbarShow: (String) -> Unit = { message ->
                    scope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                }
                if (BuildConfig.API_KEY == "") {
                    Log.e("API_KEY_MISSING", "No API key for TMDB has been provided, network errors will occur\nPlease defined the API key in the 'local.properties' file with following line:\nAPI_KEY=.....")
                    LaunchedEffect(Unit) { scope.launch { snackbarHostState.showSnackbar(message = "Missing API key, app will not work", duration = SnackbarDuration.Indefinite) } }
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { GeneralNavBar(navController) },
                    contentWindowInsets = WindowInsets(0.dp),
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    }
                ) { innerPadding ->
                    MainNavHost(
                        navController = navController,
                        onRouteChanged = {},
                        snackbarShow = snackbarShow,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
