package com.solodev.animeloom.presentation.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.solodev.animeloom.R
import com.solodev.animeloom.presentation.MainViewModel
import com.solodev.animeloom.presentation.navgraph.MainNavigation

@Composable
fun AnimeLoomSplashScreen(
    viewModel: MainViewModel,
    navHostController: NavHostController,
    isUpdateAvailable: MutableState<Boolean>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.clip(CircleShape),
            painter = painterResource(id = R.drawable.animeloom),
            contentDescription = "confy",
        )
    }

    if (!isUpdateAvailable.value) {
        MainNavigation(
            navController = navHostController,
            startDestination = viewModel.startDestination,
            onNavigate = { route -> viewModel.saveRoute(route) },
        )
    }
}