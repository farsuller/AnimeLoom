package com.solodev.animeloom.presentation.navgraph

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.toRoute
import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.presentation.MainViewModel
import com.solodev.animeloom.presentation.navgraph.component.AnimesBottomNavigation
import com.solodev.animeloom.presentation.navgraph.component.bottomNavItems
import com.solodev.animeloom.presentation.screens.bookmark.BookmarkScreen
import com.solodev.animeloom.presentation.screens.bookmark.BookmarkViewModel
import com.solodev.animeloom.presentation.screens.home.HomeAnimeViewModel
import com.solodev.animeloom.presentation.screens.home.HomeAnimesScreen
import com.solodev.animeloom.presentation.screens.home.details.AnimeDetailsScreen
import com.solodev.animeloom.presentation.screens.manga.MangaScreen
import com.solodev.animeloom.presentation.screens.manga.MangaViewModel
import com.solodev.animeloom.presentation.screens.manga.details.MangaDetailsScreen
import kotlinx.coroutines.delay

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AnimesNavigator(
    onNavigate: (String) -> Unit,
) {
    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value

    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }

    selectedItem = remember(key1 = backStackState) {
        when (backStackState?.destination?.route) {
            Route.HomeRoute.route -> 0
            Route.MangaRoute.route -> 1
            Route.BookmarkRoute.route -> 2
            else -> 0
        }
    }

    val isBottomNavBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HomeRoute.route ||
                backStackState?.destination?.route == Route.MangaRoute.route ||
                backStackState?.destination?.route == Route.BookmarkRoute.route
    }

    val mainViewModel: MainViewModel = hiltViewModel()
    val homeAnimesViewModel: HomeAnimeViewModel = hiltViewModel()
    val mangaViewModel: MangaViewModel = hiltViewModel()
    val bookmarkViewModel: BookmarkViewModel = hiltViewModel()

    val lastRoute = mainViewModel.getLastRoute()

    val animeState by homeAnimesViewModel.animeState.collectAsStateWithLifecycle()
    val trendingAnimeState by homeAnimesViewModel.trendingAnimeState.collectAsStateWithLifecycle()
    val animeHighRateState by homeAnimesViewModel.animeHighRateState.collectAsStateWithLifecycle()
    val animeRomanceState by homeAnimesViewModel.animeRomanceState.collectAsStateWithLifecycle()
    val trendingManga by homeAnimesViewModel.trendingMangaState.collectAsStateWithLifecycle()
    val isLoadingHomeData by homeAnimesViewModel.isLoadingData.collectAsStateWithLifecycle()
    val mangaState by mangaViewModel.mangaState.collectAsStateWithLifecycle()
    val categoryState by homeAnimesViewModel.categoryState.collectAsStateWithLifecycle()
    val bookmarkState = bookmarkViewModel.bookmarkState.value

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
                    items = bottomNavItems(),
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
                                    route = Route.MangaRoute.route,
                                )
                                onNavigate(Route.MangaRoute.route)
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

        SharedTransitionLayout {
            NavHost(
                modifier = Modifier.padding(bottom = bottomPadding),
                navController = navController,
                startDestination = Route.HomeRoute.route,
            ) {

                composable(Route.HomeRoute.route) {
                    HomeAnimesScreen(
                        animeState = animeState,
                        animeHighRateState = animeHighRateState,
                        animeRomanceState = animeRomanceState,
                        trendingAnimeState = trendingAnimeState,
                        mangaState = mangaState,
                        trendingMangaState = trendingManga,
                        bookmarkState = bookmarkState,
                        categoryState = categoryState,
                        isLoadingData = isLoadingHomeData,
                        onNavigate = onNavigate,
                        onPullRefresh = {
                            homeAnimesViewModel.requestApis()
                            mangaViewModel.requestApis()
                        },
                        onAnimeClick = { cover, id, localId ->
                            navController.navigate(
                                Route.AnimeDetailsRoute(
                                    animeId = id ?: "",
                                    coverImage = cover ?: "",
                                    localId = localId,
                                    isFromBookmarked = false
                                )
                            )
                        },
                        onMangaClick = { cover, id, localId ->
                            navController.navigate(
                                Route.MangaDetailsRoute(
                                    mangaId = id ?: "",
                                    coverImage = cover ?: "",
                                    localId = localId,
                                    isFromBookmarked = false
                                )
                            )
                        },
                        animatedVisibilityScope = this
                    )
                }
                composable<Route.AnimeDetailsRoute> { detail ->
                    val argsDetail = detail.toRoute<Route.AnimeDetailsRoute>()

                    AnimeDetailsScreen(
                        id = argsDetail.animeId,
                        coverImage = argsDetail.coverImage,
                        navigateUp = {
                            navController.navigateUp()
                        },
                        animatedVisibilityScope = this
                    )

                }

                composable(Route.MangaRoute.route) {
                    MangaScreen(
                        mangaState = mangaState,
                        onNavigate = onNavigate,
                        onMangaClick = { cover, id , localId ->
                            navController.navigate(
                                Route.MangaDetailsRoute(
                                    mangaId = id ?: "",
                                    coverImage = cover ?: "",
                                    localId = localId,
                                    isFromBookmarked = false
                                )
                            )
                        },
                        animatedVisibilityScope = this
                    )
                }

                composable<Route.MangaDetailsRoute> { detail ->
                    val argsDetail = detail.toRoute<Route.MangaDetailsRoute>()

                    MangaDetailsScreen(
                        id = argsDetail.mangaId,
                        coverImage = argsDetail.coverImage,
                        navigateUp = {
                            navController.navigateUp()
                        },
                        animatedVisibilityScope = this
                    )
                }

                composable(Route.BookmarkRoute.route) {
                    BookmarkScreen(
                        bookmarkState = bookmarkState,
                        onNavigate = onNavigate,
                        onAnimeClick = { cover, id, localId ->
                            navController.navigate(
                                Route.AnimeDetailsRoute(animeId = id, coverImage = cover, localId = localId, isFromBookmarked = true)
                            )
                        },
                        onMangaClick = { cover, id, localId ->
                            navController.navigate(
                                Route.MangaDetailsRoute(mangaId = id, coverImage = cover, localId = localId, isFromBookmarked = true)
                            )
                        },
                        animatedVisibilityScope = this
                    )
                }
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
