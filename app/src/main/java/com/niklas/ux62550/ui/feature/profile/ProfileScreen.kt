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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.niklas.ux62550.ui.feature.mediadetails.DrawCircle
import com.niklas.ux62550.ui.theme.LoginButtonGray
import com.niklas.ux62550.ui.theme.ProfileBtnRed
import com.niklas.ux62550.ui.theme.UX62550Theme
import com.niklas.ux62550.ui.theme.placeholderIconColor
import com.niklas.ux62550.ui.theme.RedColorGradient
import com.niklas.ux62550.ui.theme.starYellow

@Composable
@Preview(showBackground = true)
fun ProfilePreview() {
    UX62550Theme(darkTheme = true) {
        Surface {
            ProfileScreen(onNavigateToLoginRegister = {})
        }
    }
}

@Composable
fun ProfileScreen(
    onNavigateToLoginRegister: (String) -> Unit,
) {
    var nameValueTemp = remember { mutableStateOf("Your name") }
    var emailValueTemp = remember { mutableStateOf("*****@gmail.com") }
    var passwordValueTemp = remember { mutableStateOf("**********") }

    Surface(modifier = Modifier.fillMaxSize()) {
        Box {
            Box(
                modifier = Modifier
                    .size(
                        LocalConfiguration.current.screenWidthDp.dp,
                        LocalConfiguration.current.screenWidthDp.dp / 3 * 2
                    )
                    .background(Brush.verticalGradient(colorStops = RedColorGradient))
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.size(0.dp, 70.dp))

            // Profile name and picture
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Column {
                    Box {
                        DrawCircle(Modifier.size(105.dp), color = placeholderIconColor)

                        Box(Modifier.padding(((105 - 26) / 2).dp)) {
                            Icon(
                                imageVector = Icons.Outlined.AddCircleOutline,
                                contentDescription = null,
                                Modifier.size(26.dp),
                                Color.Black
                            )
                        }

                    }

                    Text(
                        text = "Profile Name",
                        style = TextStyle(fontSize = 12.sp, shadow = textShadow),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(0.dp, 4.dp, 0.dp, 0.dp)
                    )
                }
            }

            // Your top rated movies??
            Box(Modifier.size(0.dp, 30.dp))
            Text(
                text = "Your Top Rated Movies",
                style = TextStyle(fontSize = 20.sp, shadow = textShadow),
                modifier = Modifier
                    .padding(18.dp, 0.dp, 0.dp, 0.dp)
                    .align(Alignment.Start)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 12.dp, 0.dp, 0.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Red: The Movie",
                    style = TextStyle(fontSize = 12.sp, shadow = textShadow),
                    modifier = Modifier.padding(18.dp, 0.dp, 0.dp, 0.dp)
                )
                Box(Modifier.size(13.dp, 0.dp))
                for (i in 0..4) {
                    Icon(
                        modifier = Modifier.size(26.dp),
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = starYellow
                    )
                }
                Text(
                    text = "5/5",
                    style = TextStyle(
                        fontSize = 13.sp,
                        shadow = textShadow,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(4.dp, 0.dp, 0.dp, 0.dp)
                )
            }

            // Profile data
            Box(Modifier.size(0.dp, 30.dp))
            ProfileAttribute("Display Name", nameValueTemp)
            Box(Modifier.size(0.dp, 17.dp))
            ProfileAttribute("Email", emailValueTemp)
            Box(Modifier.size(0.dp, 110.dp))
            ProfileAttribute("Password", passwordValueTemp)

            Box(Modifier.size(0.dp, 20.dp))
            Button  (
                onClick = { onNavigateToLoginRegister("Sign out") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = LoginButtonGray
                ),
                modifier = Modifier
                    .size(145.dp, 45.dp)
                    .shadow(elevation = 4.dp, shape = ButtonDefaults.shape)
            ) {
                Text(
                    text = "Sign out",
                    color = Color.White,
                    style = TextStyle(fontSize = 20.sp, shadow = textShadow)
                )
            }

        }
    }
}

@Composable
fun ProfileAttribute(label: String, value: MutableState<String>) {
    Row(Modifier.fillMaxWidth(), Arrangement.Start, Alignment.CenterVertically) {
        TextField(
            value = value.value,
            modifier = Modifier
                .size(260.dp, 55.dp)
                .padding(16.dp, 0.dp, 0.dp, 0.dp),
            onValueChange = { value.value = it },
            label = { Text(text = label) },
            textStyle = TextStyle(fontSize = 15.sp)
        )
        Box(Modifier.size(22.dp, 0.dp))
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = ProfileBtnRed
            ),
            modifier = Modifier
                .size(70.dp, 23.dp)
                .shadow(elevation = 4.dp, shape = ButtonDefaults.shape),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "Edit",
                color = Color.White,
                style = TextStyle(fontSize = 15.sp, shadow = textShadow),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}
