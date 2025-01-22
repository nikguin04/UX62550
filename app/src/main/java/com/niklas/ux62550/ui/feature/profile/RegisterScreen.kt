package com.niklas.ux62550.ui.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
@Preview(showBackground = true, name = "Register preview")
fun RegisterPreview() {
    UX62550Theme {
        Surface {
            RegisterScreen(navigateBack = {}, onNavigateToProfile = {}, snackbarShow =  {})
        }
    }
}

@Composable
fun RegisterScreen(navigateBack: () -> Unit, onNavigateToProfile: (String) -> Unit, modifier: Modifier = Modifier, snackbarShow: (String) -> Unit) {
    var usernameValue = remember { mutableStateOf("") }
    var emailValue = remember { mutableStateOf("") }
    var passValue = remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Box(
            modifier = Modifier
            .size(
                    LocalConfiguration.current.screenWidthDp.dp,
                    LocalConfiguration.current.screenWidthDp.dp / 3 * 2
                )
                .background(Brush.verticalGradient(colorStops = RedColorGradient))
        )


        Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier.padding(0.dp, LocalConfiguration.current.screenWidthDp.dp / 4, 0.dp, 20.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                LogoBox(modifier = Modifier.shadow(elevation = 4.dp, shape = RoundedCornerShape(5)), size = 200.dp)
            }
            RegisterInputHolder(
                usernameValue, emailValue, passValue, onNavigateToProfile,
                snackbarShow = snackbarShow
            )

        }

        GeneralTopBar(navigateBack = navigateBack)
    }
}

@Composable
fun RegisterInputHolder(
    usernameValue: MutableState<String>,
    emailValue: MutableState<String>,
    passValue: MutableState<String>,
    onNavigateToProfile: (String) -> Unit,
    snackbarShow: (String) -> Unit) {
    Column {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp),
            value = usernameValue.value,
            onValueChange = { usernameValue.value = it },
            label = { Text("Username") }
        )
        Text(
            text = "Please choose an appropriate username",
            color = TextFieldDescColor,
            modifier = Modifier.padding(10.dp, 4.dp).align(Alignment.Start),
            style = TextStyle(fontSize = 12.sp, shadow = textShadow)
        )

        Box(Modifier.padding(0.dp, 20.dp, 0.dp, 0.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp),
            value = emailValue.value,
            onValueChange = { emailValue.value = it },
            label = { Text("Email") }
        )
        Text(
            text = "Please choose your own email",
            color = TextFieldDescColor,
            modifier = Modifier.padding(10.dp, 4.dp).align(Alignment.Start),
            style = TextStyle(fontSize = 12.sp, shadow = textShadow)
        )

        Box(Modifier.padding(0.dp, 20.dp, 0.dp, 0.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp),
            value = passValue.value,
            onValueChange = { passValue.value = it },
            label = { Text("Password") }
        )
        Text(
            text = "Please choose a safe password",
            color = TextFieldDescColor,
            modifier = Modifier.padding(10.dp, 4.dp).align(Alignment.Start),
            style = TextStyle(fontSize = 12.sp, shadow = textShadow)
        )

        Button(
            onClick = {
                FirebaseAuthController().createAccount(
                    emailValue.value,
                    passValue.value,
                    onSuccess = { snackbarShow("Successfully registered") },
                    onError = {snackbarShow("Failed to register")}
                )
                onNavigateToProfile("Register");

            },
            colors = ButtonDefaults.buttonColors(
                containerColor = RegisterButtonBlue
            ),
            modifier = Modifier
                .size(145.dp, 45.dp)
                .padding(10.dp, 0.dp)
                .shadow(elevation = 4.dp, shape = ButtonDefaults.shape)
                .align(Alignment.End)
        ) {
            Text(
                text = "Register",
                color = Color.White,
                style = TextStyle(fontSize = 20.sp, shadow = textShadow)
            )

        }
    }
}
