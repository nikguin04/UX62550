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
import androidx.compose.runtime.Composable
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
import com.niklas.ux62550.ui.feature.common.LogoBox
import com.niklas.ux62550.ui.theme.ButtonBlue
import com.niklas.ux62550.ui.theme.ButtonGray
import com.niklas.ux62550.ui.theme.RedColorGradient
import com.niklas.ux62550.ui.theme.UX62550Theme

@Composable
fun LoginRegisterScreen(
    onNavigateToLoginScreen: (String) -> Unit,
    onNavigateToRegisterScreen: (String) -> Unit,
    topModifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(3f / 2f)
            .background(Brush.verticalGradient(colorStops = RedColorGradient))
    )

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
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
        Text(
            text = "Not a member yet?",
            modifier = Modifier.padding(bottom = 12.dp),
            style = TextStyle(fontSize = 20.sp, shadow = textShadow)
        )
        Button(
            onClick = { onNavigateToRegisterScreen("Register") },
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonBlue
            ),
            modifier = Modifier
                .size(140.dp, 45.dp)
                .shadow(elevation = 4.dp, shape = ButtonDefaults.shape)
        ) {
            Text(text = "Register", color = Color.White, style = TextStyle(fontSize = 20.sp, shadow = textShadow))
        }

        Spacer(Modifier.height(50.dp))

        Text(
            text = "Already a member?",
            modifier = Modifier.padding(bottom = 12.dp),
            style = TextStyle(fontSize = 20.sp, shadow = textShadow)
        )
        Button(
            onClick = { onNavigateToLoginScreen("Login") },
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonGray
            ),
            modifier = Modifier
                .size(140.dp, 45.dp)
                .shadow(elevation = 4.dp, shape = ButtonDefaults.shape)
        ) {
            Text(text = "Sign in", color = Color.White, style = TextStyle(fontSize = 20.sp, shadow = textShadow))
        }

        Spacer(Modifier.height(140.dp))
    }
}

@Composable
@Preview(showBackground = true, device = "spec:width=411dp,height=700dp")
@Preview(showBackground = true, device = "spec:width=300dp,height=500dp", name = "Small")
fun LoginRegisterPreview() {
    UX62550Theme {
        Surface {
            LoginRegisterScreen(onNavigateToLoginScreen = {}, onNavigateToRegisterScreen = {})
        }
    }
}
