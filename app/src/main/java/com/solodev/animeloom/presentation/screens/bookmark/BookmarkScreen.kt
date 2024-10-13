package com.solodev.animeloom.presentation.screens.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.presentation.navgraph.Route


@Composable
fun BookmarkScreen(
    state: BookmarkState,
    navigateToDetails: (AnimeData) -> Unit,
    onNavigate: (String) -> Unit
) {

    LaunchedEffect(Unit) {
        onNavigate(Route.BookmarkRoute.route)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(top = 24.dp, start = 24.dp, end = 24.dp),
    ) {
        Text(
            text = "Bookmark",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(24.dp))


    }
}
