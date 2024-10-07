package com.niklas.ux62550

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.niklas.ux62550.ui.theme.UX62550Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UX62550Theme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding)//.background(Color_background)
                    )
                }
            }
        }
    }
}



@Composable
fun ScreenImage() {
    //val image = painterResource(R.drawable.ic_launcher_background)

    BoxWithRoundedImage()
}

@Composable
fun BoxWithRoundedImage() {
    // Image size, for example, 200dp
    val imageSize = 200.dp

    // Compose Box with the image and 5% rounded corners
    Box(
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

@Composable
fun PromoItem(width: Dp, height: Dp, round: Dp, color: Color, modifier: Modifier = Modifier) {

        Box(
            modifier = modifier.clip(RoundedCornerShape(round)).background(color).size(width, height).padding()

        )
}

@Composable
fun LogoBox(size: Dp) {
    // Image size, for example, 200dp
    val imageSize = size

    // Compose Box with the image and 5% rounded corners
    Box(
        modifier = Modifier.size(imageSize).clip(RoundedCornerShape(5))
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo), // Your image resource
            contentDescription = null,
            modifier = Modifier
                .size(imageSize) // Same size for the image
                .clip(RoundedCornerShape(0.00f * imageSize.value)),

            contentScale = ContentScale.Crop // Crops the image to fit the box
        )
    }
}



@Preview(showBackground = true, name = "MainPreview")
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    UX62550Theme (darkTheme = true, dynamicColor = false) {
        Surface(
            modifier = Modifier.fillMaxSize()
            //color = Color_background,
        ) {
            ScreenWithGeneralNavBar {
                Column(modifier.padding(20.dp, 20.dp)) {
                    Row(
                        modifier.fillMaxWidth().padding(FigmaPxToDp_w(19.5f)), // ConvertPxToDp(29.5f)
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        LogoBox(size= FigmaPxToDp_w(50f))

                        Box (modifier = Modifier.padding(FigmaPxToDp_w(20f), 0.dp, 0.dp ,0.dp)) {

                            Text(
                                text = "Welcome, User1",
                                style = TextStyle(
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold,
                                ),
                            )

                        }
                    }

                    HorizontalLazyRowWithSnapEffect()
                    }


                    /*Row(modifier.padding(0.dp, 20.dp, 0.dp, 0.dp)) {
                        Greeting("Niklas", modifier)
                        MovieTitle(0)
                        ScreenImage()
                    }
                    TextField(
                        value = myVar.toString(),
                        onValueChange = {
                            myVar = try {
                                it.toInt()
                            } catch (e: NumberFormatException) {
                                0
                            }
                        },
                        label = { Text("Number Label") }
                    )
                    Text(text = "Int = $myVar")*/
                }
            }
        }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalLazyRowWithSnapEffect() {
    val listState = rememberLazyListState() // To manage the scroll state

    // List of items to display in the LazyRow
    val itemsList = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")

    // LazyRow with snapping effect
    LazyRow(
        state = listState,
        flingBehavior = rememberSnapFlingBehavior(listState), // Snap to item effect
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        item { PromoItem(width= FigmaPxToDp_w(300f), height=FigmaPxToDp_h(200f), round=FigmaPxToDp_w(20f), color=Color(0xFF000022), modifier = Modifier.padding(FigmaPxToDp_w(10f) ) ) }
        item { PromoItem(width= FigmaPxToDp_w(300f), height=FigmaPxToDp_h(200f), round=FigmaPxToDp_w(20f), color=Color(0xFF006600), modifier = Modifier.padding(FigmaPxToDp_w(10f) ) ) }
        item { PromoItem(width= FigmaPxToDp_w(300f), height=FigmaPxToDp_h(200f), round=FigmaPxToDp_w(20f), color=Color(0xFF990000), modifier = Modifier.padding(FigmaPxToDp_w(10f) ) ) }
        /*items(itemsList) { item ->
            // Each item in the row
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .width(200.dp)
                    .height(150.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = item, fontSize = 20.sp)
                }
            }
        }*/
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

    NavigationBar  {
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