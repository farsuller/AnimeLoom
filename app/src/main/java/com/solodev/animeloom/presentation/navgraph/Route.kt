package com.solodev.animeloom.presentation.navgraph

import kotlinx.serialization.Serializable

@Serializable
sealed class Route(val route: String) {

    @Serializable
    data object SplashRoute : Route(route = "splash_route")

    @Serializable
    data object OnboardingRoute : Route(route = "onboarding_route")

    @Serializable
    data object HomeRoute : Route(route = "home_route")

    @Serializable
    data object MangaRoute : Route(route = "manga_route")

    @Serializable
    data object BookmarkRoute : Route(route = "bookmark_route")

    @Serializable
    data class AnimeDetailsRoute(
        val animeId: String,
        val coverImage: String,
        val localId: String,
        val isFromBookmarked: Boolean,
    )

    @Serializable
    data class MangaDetailsRoute(
        val mangaId: String,
        val coverImage: String,
        val localId: String,
        val isFromBookmarked: Boolean,
    )

    @Serializable
    data class BookmarkDetailsRoute(val bookmarkId: String, val coverImage: String)

    @Serializable
    data object AnimesRoute : Route(route = "animes")

    @Serializable
    data object AppStartNavigation : Route(route = "appStartNav")

    @Serializable
    data object AnimesNavigation : Route(route = "animesNav")

    @Serializable
    data object LoomNavigation : Route(route = "loomNav")
}
