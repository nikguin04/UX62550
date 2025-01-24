package com.niklas.ux62550.ui.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.niklas.ux62550.data.remote.FirebaseAuthController
import com.niklas.ux62550.navigation.GeneralTopBar
import com.niklas.ux62550.ui.feature.common.LogoBox
import com.niklas.ux62550.ui.theme.RedColorGradient
import com.niklas.ux62550.ui.theme.RegisterButtonBlue
import com.niklas.ux62550.ui.theme.TextFieldDescColor
import com.niklas.ux62550.ui.theme.UX62550Theme

@Composable
fun LoginScreen(navigateBack: () -> Unit, onNavigateToProfile: (String) -> Unit, snackbarShow: (String) -> Unit, topModifier: Modifier = Modifier) {
    var emailValue = remember { mutableStateOf("") }
    var passValue = remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 2f)
                .background(Brush.verticalGradient(colorStops = RedColorGradient))
        )
    }

    Column(
        modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = topModifier)
        Row(
            modifier = Modifier
                .padding(0.dp, LocalConfiguration.current.screenWidthDp.dp / 4, 0.dp, 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            LogoBox(size = 200.dp, modifier = Modifier.shadow(elevation = 4.dp, shape = RoundedCornerShape(5)))
        }
        LoginInputHolder(
            emailValue, passValue, onNavigateToProfile,
            snackbarShow = snackbarShow
        )
    }

    GeneralTopBar(navigateBack = navigateBack)
}

@Composable
fun LoginInputHolder(
    emailValue: MutableState<String>,
    passValue: MutableState<String>,
    onNavigateToProfile: (String) -> Unit,
    snackbarShow: (String) -> Unit
) {
    Column(Modifier.padding(horizontal = 10.dp)) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = emailValue.value,
            onValueChange = { emailValue.value = it },
            label = { Text("Username or Email") }
        )
        Text(
            text = "Input your username or email",
            color = TextFieldDescColor,
            modifier = Modifier
                .padding(vertical = 4.dp)
                .align(Alignment.Start),
            style = TextStyle(fontSize = 12.sp, shadow = textShadow)
        )

        Spacer(Modifier.height(20.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = passValue.value,
            onValueChange = { passValue.value = it },
            label = { Text("Password") }
        )
        Text(
            text = "Input your password",
            color = TextFieldDescColor,
            modifier = Modifier
                .padding(vertical = 4.dp)
                .align(Alignment.Start),
            style = TextStyle(fontSize = 12.sp, shadow = textShadow)
        )

        Button(
            onClick = {
                FirebaseAuthController().signIn(
                    emailValue.value,
                    passValue.value,
                    onSuccess = { snackbarShow("Successfully logged in") },
                    onError = { snackbarShow("Failed to sign in") }
                )
                onNavigateToProfile("Login")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = RegisterButtonBlue
            ),
            modifier = Modifier
                .size(145.dp, 45.dp)
                .shadow(elevation = 4.dp, shape = ButtonDefaults.shape)
                .align(Alignment.End)
        ) {
            Text(
                text = "Sign in",
                color = Color.White,
                style = TextStyle(fontSize = 20.sp, shadow = textShadow)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun LoginPreview() {
    UX62550Theme {
        Surface {
            LoginScreen(navigateBack = {}, onNavigateToProfile = {}, snackbarShow = {})
        }
    }
}
