package com.solodev.animeloom.presentation.screens.manga

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.solodev.animeloom.presentation.navgraph.Route
import com.solodev.animeloom.presentation.screens.home.CategoryState


@Composable
fun CategoryScreen(
    categoryState: CategoryState,
    onNavigate: (String) -> Unit
) {

    LaunchedEffect(Unit) {
        onNavigate(Route.MangaRoute.route)
    }

    Column(
        modifier = Modifier
            .padding(top = 24.dp, start = 24.dp, end = 24.dp)
            .statusBarsPadding()
            .fillMaxSize(),
    ) {
        Text(
            text = "Categories",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface,
        )


        when {
            categoryState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            categoryState.errorMessage != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Error: ${categoryState.errorMessage}")
                }
            }

            categoryState.categories != null ->{
                LazyColumn {
                    items(categoryState.categories){ category ->
                        Text(text = category.attributes.title ?: "No title")
                    }
                }
            }
        }




        Spacer(modifier = Modifier.height(24.dp))

    }
}
