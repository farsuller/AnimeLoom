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
import com.solodev.animeloom.presentation.MainViewModel
import com.solodev.animeloom.presentation.components.AnimesBottomNavigation
import com.solodev.animeloom.presentation.components.bottomNavItems
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

    val upcomingAnimeState by homeAnimesViewModel.animeUpcomingState.collectAsStateWithLifecycle()
    val trendingAnimeState by homeAnimesViewModel.popularAnimeState.collectAsStateWithLifecycle()
    val animeHighRateState by homeAnimesViewModel.animeHighRateState.collectAsStateWithLifecycle()
    val animeNewReleaseState by homeAnimesViewModel.animeNewReleaseState.collectAsStateWithLifecycle()
    val categoryState by homeAnimesViewModel.categoryState.collectAsStateWithLifecycle()
    val animeByCategoryState by homeAnimesViewModel.animeByCategoryState.collectAsStateWithLifecycle()
    val animeBySeeAllState by homeAnimesViewModel.animeBySeeAllState.collectAsStateWithLifecycle()
    val isLoadingHomeData by homeAnimesViewModel.isLoadingData.collectAsStateWithLifecycle()

    val mangaState by mangaViewModel.combinedMangaState.collectAsStateWithLifecycle()
    val isLoadingMangaData by mangaViewModel.isLoadingData.collectAsStateWithLifecycle()

    val bookmarkState = bookmarkViewModel.bookmarkState.collectAsStateWithLifecycle()
    val isLoadingBookmarkData by bookmarkViewModel.isLoadingData.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        if (lastRoute.isNotEmpty() && lastRoute != Route.AnimesNavigation.route) {
            delay(800L)
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
                        upcomingAnimeState = upcomingAnimeState,
                        animeHighRateState = animeHighRateState,
                        animeNewReleaseState = animeNewReleaseState,
                        trendingAnimeState = trendingAnimeState,
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
                                    isFromBookmarked = false,
                                ),
                            )
                        },
                        animatedVisibilityScope = this,
                        selectedCategory = { category ->
                            if (category == null) return@HomeAnimesScreen
                            homeAnimesViewModel.getAnimesByCategory(categorySelected = category.attributes.slug)
                        },
                        selectedSeeAll = { selectedSeeAll ->
                            if (selectedSeeAll == null) return@HomeAnimesScreen
                            homeAnimesViewModel.getAnimeBySeeAll(selectedSeeAll = selectedSeeAll)
                        },
                        animeByCategoryState = animeByCategoryState,
                        animeBySeeAllState = animeBySeeAllState,
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
                        animatedVisibilityScope = this,
                    )
                }

                composable(Route.MangaRoute.route) {
                    MangaScreen(
                        mangaState = mangaState,
                        onNavigate = onNavigate,
                        isLoadingData = isLoadingMangaData,
                        onMangaClick = { cover, id, localId ->
                            navController.navigate(
                                Route.MangaDetailsRoute(
                                    mangaId = id ?: "",
                                    coverImage = cover ?: "",
                                    localId = localId,
                                    isFromBookmarked = false,
                                ),
                            )
                        },
                        animatedVisibilityScope = this,
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
                        animatedVisibilityScope = this,
                    )
                }

                composable(Route.BookmarkRoute.route) {
                    BookmarkScreen(
                        bookmarkState = bookmarkState.value,
                        onNavigate = onNavigate,
                        isLoadingData = isLoadingBookmarkData,
                        onAnimeClick = { cover, id, localId ->
                            navController.navigate(
                                Route.AnimeDetailsRoute(
                                    animeId = id,
                                    coverImage = cover,
                                    localId = localId,
                                    isFromBookmarked = true,
                                ),
                            )
                        },
                        onMangaClick = { cover, id, localId ->
                            navController.navigate(
                                Route.MangaDetailsRoute(
                                    mangaId = id,
                                    coverImage = cover,
                                    localId = localId,
                                    isFromBookmarked = true,
                                ),
                            )
                        },
                        animatedVisibilityScope = this,
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
