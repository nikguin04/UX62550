package com.niklas.ux62550.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController

data class NavItem(
    val name: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    val onNavigate: (NavHostController) -> Unit
)

@Composable
fun GeneralNavBar(navController: NavHostController) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(
        NavItem(name = "Home", icon = Icons.AutoMirrored.Outlined.List, selectedIcon = Icons.AutoMirrored.Filled.List, onNavigate = { it.navigateAndClearBackStack(Route.HomeScreen) }),
        NavItem(name = "Search", icon = Icons.Outlined.Search, selectedIcon = Icons.Filled.Search, onNavigate = { it.navigateAndClearBackStack(Route.SearchScreen) }),
        NavItem(name = "Watch", icon = Icons.Outlined.BookmarkBorder, selectedIcon = Icons.Filled.Bookmark, onNavigate = { it.navigateAndClearBackStack(Route.WatchScreen) }),
        // TODO: Do not just route to account, instead check if user is logged in and route to profile/loginregister
        NavItem(name = "Account", icon = Icons.Outlined.AccountCircle, selectedIcon = Icons.Filled.AccountCircle, onNavigate = { it.navigateAndClearBackStack(Route.ProfileScreen) })
    )

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (selectedItem == index) item.selectedIcon else item.icon,
                        contentDescription = item.name
                    )
                },
                label = { Text(item.name) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    item.onNavigate(navController)
                }
            )
        }
    }
}
