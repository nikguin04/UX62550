package com.niklas.ux62550

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.niklas.ux62550.ui.theme.Color_background
import com.niklas.ux62550.ui.theme.Color_container
import com.niklas.ux62550.ui.theme.Purple80
import com.niklas.ux62550.ui.theme.UX62550Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UX62550Theme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding).background(Color_background)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier.padding((20.dp)),
    )
}

@Composable fun MovieTitle(id: Int, modifier: Modifier = Modifier) {
    val textArr: Array<String> = arrayOf("Movie1", "Series2")
    androidx.compose.material3.Text(
        text = textArr[id],
        fontSize = 30.sp, //TextUnit(30f, TextUnitType.Sp)
        lineHeight = 32.sp,
        modifier = modifier
    )
}

@Composable
fun ScreenImage(message: String, from: String, modifier: Modifier = Modifier) {
    val image = painterResource(R.drawable.ic_launcher_background)

    BoxWithRoundedImage()
}

@Composable
fun BoxWithRoundedImage() {
    // Image size, for example, 200dp
    val imageSize = 200.dp

    // Compose Box with the image and 5% rounded corners
    androidx.compose.foundation.layout.Box(
        modifier = Modifier.size(imageSize).clip(RoundedCornerShape(5))
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background), // Your image resource
            contentDescription = null,
            modifier = Modifier
                .size(imageSize) // Same size for the image
                .clip(RoundedCornerShape(0.05f * imageSize.value)), // Apply 5% rounded corners dynamically
            contentScale = ContentScale.Crop // Crops the image to fit the box
        )
    }
}

//@Preview(showBackground = true)
@Composable
fun ScreenImageA() {
    ScreenImage(
        message = "Happy Birthday Sam!",
        from = "From Emma",

    )
}

@Preview(showBackground = true)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    UX62550Theme (darkTheme = true, dynamicColor = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            //color = Color_background,
        ) {
            var varb by remember { mutableIntStateOf(0) }
            ScreenWithGeneralNavBar() {
                Column(modifier.padding(20.dp, 20.dp)) {
                    Row(modifier.padding(0.dp, 20.dp, 0.dp, 0.dp)) {
                        Greeting("Niklas", modifier)
                        MovieTitle(0)
                        ScreenImage("a", "b")
                    }
                    TextField(
                        value = varb.toString(),
                        onValueChange = {
                            varb = try {
                                it.toInt()
                            } catch (e: NumberFormatException) {
                                0
                            }
                        },
                        label = { Text("Number Label") }
                    )
                    Text(text = "Int = $varb")
                }
            }
        }


    }
}

@Composable
fun ScreenWithGeneralNavBar(content: @Composable (PaddingValues) -> Unit) {
    Scaffold ( // NAVBAR COLUMN
        topBar = {

        },

        bottomBar = { GeneralNavBar() },
        content = content
    )
}

@Composable
fun GeneralNavBar() {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Home", "Search", "Watch", "Account")
    val selectedIcons = listOf(Icons.AutoMirrored.Outlined.List, Icons.Outlined.Search, Icons.Outlined.Bookmark, Icons.Outlined.AccountCircle)
    val unselectedIcons =
        listOf(Icons.AutoMirrored.Outlined.List, Icons.Outlined.Search, Icons.Outlined.Bookmark, Icons.Outlined.AccountCircle)

    NavigationBar (
        //containerColor = MaterialTheme.colorScheme.background,
        //tonalElevation = 1000.dp
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        if (selectedItem == index) selectedIcons[index] else unselectedIcons[index],
                        contentDescription = item
                    )
                },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = { selectedItem = index },
                /*colors = NavigationBarItemColors(
                    selectedIconColor = fromToken(NavigationBarTokens.ActiveIconColor),
                    selectedTextColor = fromToken(NavigationBarTokens.ActiveLabelTextColor),
                    selectedIndicatorColor = fromToken(NavigationBarTokens.ActiveIndicatorColor),
                    unselectedIconColor = fromToken(NavigationBarTokens.InactiveIconColor),
                    unselectedTextColor = fromToken(NavigationBarTokens.InactiveLabelTextColor),
                    disabledIconColor = fromToken(NavigationBarTokens.InactiveIconColor).copy(alpha = DisabledAlpha),
                    disabledTextColor = fromToken(NavigationBarTokens.InactiveLabelTextColor).copy(alpha = DisabledAlpha),
                )*/
            )
        }
    }
}