package com.solodev.animeloom.presentation.navgraph

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.presentation.MainViewModel
import com.solodev.animeloom.presentation.navgraph.component.BottomNavigationItem
import com.solodev.animeloom.presentation.navgraph.component.AnimesBottomNavigation
import com.solodev.animeloom.presentation.screens.bookmark.BookmarkViewModel
import com.solodev.animeloom.presentation.screens.home.HomeScreen
import com.solodev.animeloom.presentation.screens.home.HomeViewModel
import com.solodev.animeloom.presentation.screens.search.SearchViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AnimesNavigator(
    onNavigate: (String) -> Unit,
) {
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = Icons.Filled.Home, text = "Home"),
            BottomNavigationItem(icon = Icons.Filled.Search, text = "Search"),
            BottomNavigationItem(icon = Icons.Filled.Bookmark, text = "Bookmark"),
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value

    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }

    val mainViewModel: MainViewModel = hiltViewModel()

    selectedItem = remember(key1 = backStackState) {
        when (backStackState?.destination?.route) {
            Route.HomeRoute.route -> 0
            Route.SearchRoute.route -> 1
            Route.BookmarkRoute.route -> 2
            else -> 0
        }
    }

    val isBottomNavBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HomeRoute.route ||
                backStackState?.destination?.route == Route.SearchRoute.route ||
                backStackState?.destination?.route == Route.BookmarkRoute.route
    }

    val lastRoute = mainViewModel.getLastRoute()

    LaunchedEffect(key1 = Unit) {
        delay(300L)
        if (lastRoute.isNotEmpty() && lastRoute != Route.AnimesNavigation.route) {
            navigateToTap(
                navController = navController,
                route = lastRoute,
            )
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomNavBarVisible) {
                AnimesBottomNavigation(
                    items = bottomNavigationItems,
                    selected = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> {
                                navigateToTap(
                                    navController = navController,
                                    route = Route.HomeRoute.route,
                                )
                                onNavigate(Route.HomeRoute.route)
                            }

                            1 -> {
                                navigateToTap(
                                    navController = navController,
                                    route = Route.SearchRoute.route,
                                )
                                onNavigate(Route.SearchRoute.route)
                            }

                            2 -> {
                                navigateToTap(
                                    navController = navController,
                                    route = Route.BookmarkRoute.route,
                                )
                                onNavigate(Route.BookmarkRoute.route)
                            }
                        }
                    },
                )
            }
        },
    ) {
        val bottomPadding = it.calculateBottomPadding()

        NavHost(
            modifier = Modifier.padding(bottom = bottomPadding),
            navController = navController,
            startDestination = Route.HomeRoute.route,
        ) {

            composable(Route.HomeRoute.route) {

                SharedTransitionLayout {

                    val viewModel: HomeViewModel = hiltViewModel()
                    val bookmarkViewModel: BookmarkViewModel = hiltViewModel()
                    val animeList by viewModel.animeState.collectAsStateWithLifecycle()

                    val state = bookmarkViewModel.state.value

                    HomeScreen(
                        animeState = animeList,
                        navigateToSearch = {
                            navigateToTap(navController = navController, Route.SearchRoute.route)
                        },
                        navigateToDetails = { anime ->
                            navigateToDetails(navController = navController, anime = anime)
                        },
                        bookmarkState = state,
                        onNavigate = onNavigate,
                        onPullRefresh = {
                            viewModel.getAnimes()
                        }
                    )
                }


            }

            composable(Route.SearchRoute.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                val state = viewModel.state.value


            }

            composable(Route.BookmarkRoute.route) {
                val viewModel: BookmarkViewModel = hiltViewModel()
                val state = viewModel.state.value

            }

            composable<Route.AnimeDetailsRoute> {

            }
        }
    }
}

fun navigateToTap(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}

private fun navigateToDetails(navController: NavController, anime: AnimeData) {
    navController.currentBackStackEntry?.savedStateHandle?.set("anime", anime)
    navController.navigate(route = Route.AnimeDetailsRoute)
}
