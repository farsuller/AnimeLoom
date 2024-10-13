package com.solodev.animeloom.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.solodev.animeloom.presentation.screens.onboarding.OnboardingScreen
import com.solodev.animeloom.presentation.screens.onboarding.OnboardingViewModel

@Composable
fun NavGraph(
    startDestination: String,
    onNavigate: (String) -> Unit,
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnboardingRoute.route,
        ){
            composable(Route.OnboardingRoute.route) {
                val viewModel: OnboardingViewModel = hiltViewModel()
                OnboardingScreen(event = viewModel::onEvent)
            }
        }

        navigation(
            route = Route.AnimesNavigation.route,
            startDestination = Route.AnimesRoute.route,
        ){
            composable(Route.AnimesRoute.route) {
                AnimesDashboard(
                    onNavigate = onNavigate
                )
            }
        }


    }
}
