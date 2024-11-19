package com.solodev.animeloom.presentation.navgraph.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun bottomNavItems() = remember {
    listOf(
        BottomNavigationItem(icon = Icons.Filled.Home, text = "Home"),
        BottomNavigationItem(icon = Icons.Filled.Category, text = "Manga"),
        BottomNavigationItem(icon = Icons.Filled.Bookmark, text = "Bookmark"),
    )
}
