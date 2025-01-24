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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.niklas.ux62550.data.remote.FirebaseAuthController
import com.niklas.ux62550.ui.feature.common.DrawCircle
import com.niklas.ux62550.ui.theme.LoginButtonGray
import com.niklas.ux62550.ui.theme.RedColorGradient
import com.niklas.ux62550.ui.theme.UX62550Theme
import com.niklas.ux62550.ui.theme.placeholderIconColor
import com.niklas.ux62550.ui.theme.starYellow

@Composable
@Preview(showBackground = true)
fun ProfilePreview() {
    UX62550Theme {
        Surface {
            ProfileScreen(onNavigateToLoginRegister = {})
        }
    }
}
@Composable
fun ProfileScreen(viewModel: ProfileViewModel = viewModel(), onNavigateToLoginRegister: (String) -> Unit, topModifier: Modifier = Modifier) {
    when (val profile = viewModel.profileState.collectAsState().value) {
        UserData.Empty -> {}
        is UserData.Data -> {
            ProfileContent(profile = profile, onNavigateToLoginRegister = onNavigateToLoginRegister, topModifier = topModifier)
        }
        UserData.Error -> {}
    }
}

@Composable
fun ProfileContent(
    onNavigateToLoginRegister: (String) -> Unit,
    profile: UserData.Data,
    topModifier: Modifier = Modifier,
) {
    val nameValueTemp = remember { mutableStateOf(profile.userData.name) }
    val emailValueTemp = remember { mutableStateOf(profile.userData.email) }
    val passwordValueTemp = remember { mutableStateOf("**********") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(3f / 2f)
            .background(Brush.verticalGradient(colorStops = RedColorGradient))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(topModifier.height(70.dp))

        // Profile name and picture
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Column {
                Box {
                    DrawCircle(placeholderIconColor, Modifier.size(105.dp))

                    Box(Modifier.padding(((105 - 26) / 2).dp)) {
                        Icon(
                            imageVector = Icons.Outlined.AddCircleOutline,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            tint = Color.Black
                        )
                    }
                }

                Text(
                    text = profile.userData.name,
                    style = TextStyle(fontSize = 12.sp, shadow = textShadow),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 4.dp)
                )
            }
        }

        // Your top rated movies??
        Spacer(Modifier.height(30.dp))
        Text(
            text = "Your Top Rated Movies",
            style = TextStyle(fontSize = 20.sp, shadow = textShadow),
            modifier = Modifier
                .padding(start = 18.dp)
                .align(Alignment.Start)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Red: The Movie",
                style = TextStyle(fontSize = 12.sp, shadow = textShadow),
                modifier = Modifier.padding(start = 18.dp)
            )
            Spacer(Modifier.width(13.dp))
            repeat (5) {
                Icon(
                    modifier = Modifier.size(18.dp),
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
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        // Profile data
        Spacer(Modifier.height(30.dp))
        ProfileAttribute("Display Name", nameValueTemp)
        Spacer(Modifier.height(17.dp))
        ProfileAttribute("Email", emailValueTemp)
        Spacer(Modifier.height(110.dp))
        ProfileAttribute("Password", passwordValueTemp)

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = { FirebaseAuthController().logout(); onNavigateToLoginRegister("Sign out") },
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

@Composable
fun ProfileAttribute(label: String, value: MutableState<String>) {
    Row(Modifier.fillMaxWidth(), Arrangement.Start, Alignment.CenterVertically) {
        Column(Modifier.padding(horizontal = 20.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray)
                    .padding(6.dp)
                    .shadow(
                        elevation = 15.dp,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Text(
                    text = label,
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        shadow = Shadow(blurRadius = 5f)
                    ),
                    modifier = Modifier.padding(start = 10.dp)
                )
                Text(
                    text = value.value,
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.White,
                        shadow = Shadow(blurRadius = 5f)
                    ),
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp)
                        .align(Alignment.CenterStart)
                )
            }
        }
    }
}
