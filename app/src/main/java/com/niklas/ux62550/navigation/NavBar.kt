package com.niklas.ux62550.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun ScreenWithGeneralNavBar(navController: NavHostController, content: @Composable (ColumnScope) -> Unit) {
    Scaffold ( // NAVBAR COLUMN
        topBar = {

        },

        bottomBar = { GeneralNavBar(navController) },
    ) {
        innerPadding ->
        Column (modifier = Modifier.padding(innerPadding).fillMaxSize(), content = content)
    }
        
}

@Composable
fun GeneralNavBar(navController: NavHostController) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Home", "Search", "Watch", "Account")

    val routes: List<Route> = listOf(
        Route.HomeScreen,
        Route.HomeScreen,
        Route.HomeScreen,
        Route.LoginRegisterScreen
    ) // TODO: Do not just route to account, instead check if user is logged in and route to profile/loginregister

    val selectedIcons = listOf(Icons.AutoMirrored.Outlined.List, Icons.Outlined.Search, Icons.Outlined.Bookmark, Icons.Outlined.AccountCircle)
    val unselectedIcons =
        listOf(Icons.AutoMirrored.Outlined.List, Icons.Outlined.Search, Icons.Outlined.Bookmark, Icons.Outlined.AccountCircle)

    NavigationBar (
        //containerColor = colorScheme.contentColorFor(NavigationBarDefaults.containerColor)
        //containerColor = colorScheme.Primary
    )  {
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
                onClick = {
                    selectedItem = index;
                    navController.navigate<Route>(route = routes[index]) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true // This removes the start destination from the backstack
                        }
                        // Set launchSingleTop to prevent multiple copies of the same destination on the backstack
                        launchSingleTop = true
                    }
                },
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