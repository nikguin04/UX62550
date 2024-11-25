package com.niklas.ux62550.ui.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.niklas.ux62550.models.figmaPxToDp_w
import com.niklas.ux62550.ui.feature.common.LogoBox
import com.niklas.ux62550.ui.theme.RedColorGradient
import com.niklas.ux62550.ui.theme.RegisterButtonBlue
import com.niklas.ux62550.ui.theme.TextFieldDescColor
import com.niklas.ux62550.ui.theme.UX62550Theme

@Composable
@Preview(showBackground = true, name = "Register preview")
fun RegisterPreview() {
    UX62550Theme(darkTheme = true, dynamicColor = false) {
        Surface {
            RegisterScreen()
        }
    }
}

@Composable
fun RegisterScreen() {
    var usenameValue by remember { mutableStateOf("") }
    var emailValue by remember { mutableStateOf("") }
    var passValue by remember { mutableStateOf("") }
    Surface(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Box(
            modifier = Modifier
                .size(
                    LocalConfiguration.current.screenWidthDp.dp,
                    LocalConfiguration.current.screenWidthDp.dp / 3 * 2
                )
                .background(Brush.verticalGradient(colorStops = RedColorGradient))
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(0.dp, figmaPxToDp_w(164f), 0.dp, figmaPxToDp_w(20f))
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                LogoBox(
                    modifier = Modifier.shadow(elevation = 4.dp, shape = RoundedCornerShape(5)),
                    size = figmaPxToDp_w(180f)
                )
            }

            // USERNAME INPUT
            TextField(
                modifier = Modifier.size(figmaPxToDp_w(328f), figmaPxToDp_w(56f)),
                value = usenameValue,
                onValueChange = { usenameValue = it },
                label = { Text("Username") }
            )
            Text(
                text = "Please choose an appropriate username",
                color = TextFieldDescColor,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(figmaPxToDp_w(33f), figmaPxToDp_w(4f), 0.dp, 0.dp),
                style = TextStyle(fontSize = 12.sp, shadow = textShadow)
            )

            // EMAIL INPUT
            Box(Modifier.padding(0.dp, figmaPxToDp_w(20f), 0.dp, 0.dp))

            TextField(
                modifier = Modifier.size(figmaPxToDp_w(328f), figmaPxToDp_w(56f)),
                value = emailValue,
                onValueChange = { emailValue = it },
                label = { Text("Email") }
            )
            Text(
                text = "Please choose your own email",
                color = TextFieldDescColor,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(figmaPxToDp_w(33f), figmaPxToDp_w(4f), 0.dp, 0.dp),
                style = TextStyle(fontSize = 12.sp, shadow = textShadow)
            )

            Box(Modifier.padding(0.dp, figmaPxToDp_w(20f), 0.dp, 0.dp))

            // PASSWORD INPUT
            TextField(
                modifier = Modifier.size(figmaPxToDp_w(328f), figmaPxToDp_w(56f)),
                value = passValue,
                onValueChange = { passValue = it },
                label = { Text("Password") }
            )
            Text(
                text = "Please choose a safe password",
                color = TextFieldDescColor,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(figmaPxToDp_w(33f), figmaPxToDp_w(4f), 0.dp, 0.dp),
                style = TextStyle(fontSize = 12.sp, shadow = textShadow)
            )

            Box(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(figmaPxToDp_w(15f), 0.dp)
            ) {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RegisterButtonBlue
                    ),
                    modifier = Modifier
                        .size(figmaPxToDp_w(120f), figmaPxToDp_w(40f))
                        .shadow(elevation = 4.dp, shape = ButtonDefaults.shape)
                ) {
                    Text(
                        text = "Register",
                        color = Color.White,
                        style = TextStyle(fontSize = 20.sp, shadow = textShadow)
                    )
                }
            }

            Box(modifier = Modifier.size(0.dp, figmaPxToDp_w(142f)))
        }
    }
}
