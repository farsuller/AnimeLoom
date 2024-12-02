package com.solodev.animeloom.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.solodev.animeloom.presentation.MainViewModel
import com.solodev.animeloom.presentation.screens.splash.LoomSplashScreen

@Composable
fun SplashNavigation(
    viewModel: MainViewModel,
    isUpdateAvailable: Boolean,
) {
    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = Route.SplashRoute.route,
    ) {
        composable(Route.SplashRoute.route) {
            LoomSplashScreen(
                onNavigateToMain = {
                    navHostController.navigate(Route.LoomNavigation.route) {
                        popUpTo(Route.SplashRoute.route) { inclusive = true }
                    }
                },
                isUpdateAvailable = isUpdateAvailable,
            )
        }

        composable(Route.LoomNavigation.route) {
            val startDestination = if (isUpdateAvailable) Route.SplashRoute.route else viewModel.startDestination

            MainNavigation(
                startDestination = startDestination,
                onNavigate = { route -> viewModel.saveRoute(route) },
            )
        }
    }
}
