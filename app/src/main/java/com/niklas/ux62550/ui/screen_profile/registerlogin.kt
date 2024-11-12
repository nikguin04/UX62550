package com.niklas.ux62550.ui.screen_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.niklas.ux62550.models.MediaItem
import com.niklas.ux62550.models.figmaPxToDp_w
import com.niklas.ux62550.navigation.ScreenWithGeneralNavBar
import com.niklas.ux62550.ui.screen_home.HomeScreen
import com.niklas.ux62550.ui.screen_home.LogoBox
import com.niklas.ux62550.ui.screen_home.MediaItemsViewModel
import com.niklas.ux62550.ui.screen_home.ScreenHome
import com.niklas.ux62550.ui.theme.LoginButtonGray
import com.niklas.ux62550.ui.theme.RegisterButtonBlue
import com.niklas.ux62550.ui.theme.UX62550Theme
import com.niklas.ux62550.ui.theme.redColorGradient

@Composable
@Preview(showBackground = true, name = "Register and login pewview")
fun RegisterLoginPreview() {
    UX62550Theme (darkTheme = true, dynamicColor = false) {
        HomeScreen(onNavigateToLoginScreen = {}, onNavigateToRegisterScreen = {})
    }
}

@Composable
fun HomeScreen(
    onNavigateToLoginScreen: (String) -> Unit,
    onNavigateToRegisterScreen: (String) -> Unit,
    //profileViewModel: ProfileViewModel = viewModel()
) {

    //val mediaItemsViewModel: MediaItemsViewModel by viewModels()
    //val uiState = profileViewModel.profileState.collectAsState().value
    Surface(
        modifier = Modifier.fillMaxSize()
        //color = Color_background,
    ) {
        val content: @Composable (PaddingValues) -> Unit = {}
        ScreenWithGeneralNavBar() {

            Box {
                Box(
                    modifier = Modifier
                        .size(
                            LocalConfiguration.current.screenWidthDp.dp,
                            LocalConfiguration.current.screenWidthDp.dp / 3 * 2
                        )
                        .background(Brush.verticalGradient(colorStops = redColorGradient))
                )
                Row(
                    modifier = Modifier.padding(0.dp, figmaPxToDp_w(164f), 0.dp, 0.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    LogoBox(figmaPxToDp_w(180f))
                }
            }


            Row (
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Bottom
            ) {
                Column (modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Already a member?", fontSize = 20.sp,
                        modifier = Modifier.padding(0.dp,0.dp,0.dp,figmaPxToDp_w(10f)))
                    Button(
                        onClick = {
                            onNavigateToLoginScreen("Register")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RegisterButtonBlue
                        ),
                        modifier = Modifier.size(figmaPxToDp_w(120f), figmaPxToDp_w(40f))
                    ) {
                        Text(text = "Register", fontSize = 20.sp, color = Color.White,)
                    }

                    Box(Modifier.size(0.dp, figmaPxToDp_w(60f)))

                    Text(text = "Not a member yet?", fontSize = 20.sp,
                        modifier = Modifier.padding(0.dp,0.dp,0.dp,figmaPxToDp_w(10f)))
                    Button(
                        onClick = {
                            onNavigateToLoginScreen("Login")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LoginButtonGray
                        ),
                        modifier = Modifier.size(figmaPxToDp_w(120f), figmaPxToDp_w(40f))
                    ) {
                        Text(text = "Sign in", fontSize = 20.sp, color = Color.White)
                    }

                    Box(modifier = Modifier.size(0.dp,figmaPxToDp_w(118f))) { }
                }
            }

        }
    }
}