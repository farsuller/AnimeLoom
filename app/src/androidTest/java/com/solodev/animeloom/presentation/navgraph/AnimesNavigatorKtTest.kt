package com.solodev.animeloom.presentation.navgraph

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.solodev.animeloom.presentation.navgraph.component.AnimesBottomNavigation
import com.solodev.animeloom.presentation.navgraph.component.BottomNavigationItem
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AnimesNavigatorKtTest {

    @get: Rule
    val composeTestRule = createComposeRule()

    private var items = listOf(
        BottomNavigationItem(icon = Icons.Filled.Home, text = "Home"),
        BottomNavigationItem(icon = Icons.Filled.Search, text = "Manga"),
        BottomNavigationItem(icon = Icons.Filled.Bookmark, text = "Bookmark"),
    )

    @Test
    fun navBar_Assert_animesNavigator() {
        composeTestRule.setContent {
            AnimesBottomNavigation(
                items = items,
                selected = 0,
                onItemClick = {},
            )
        }

        // Verify if "Home" item is displayed
        composeTestRule.onNodeWithText("Home").assertExists()

        // Verify if "Search" item is displayed
        composeTestRule.onNodeWithText("Manga").assertExists()

        // Verify if "Bookmark" item is displayed
        composeTestRule.onNodeWithText("Bookmark").assertExists()
    }
}
