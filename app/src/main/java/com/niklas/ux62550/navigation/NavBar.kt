package com.niklas.ux62550.navigation

import androidx.compose.foundation.layout.PaddingValues
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

@Composable
fun ScreenWithGeneralNavBar(content: @Composable (PaddingValues) -> Unit) {
    Scaffold(
        topBar = {},
        bottomBar = { GeneralNavBar() },
        content = content
    )
}

@Composable
fun GeneralNavBar() {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Home", "Search", "Watch", "Account")
    val selectedIcons = listOf(Icons.AutoMirrored.Outlined.List, Icons.Outlined.Search, Icons.Outlined.Bookmark, Icons.Outlined.AccountCircle)
    val unselectedIcons = listOf(Icons.AutoMirrored.Outlined.List, Icons.Outlined.Search, Icons.Outlined.Bookmark, Icons.Outlined.AccountCircle)

    NavigationBar {
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
                onClick = { selectedItem = index }
            )
        }
    }
}
