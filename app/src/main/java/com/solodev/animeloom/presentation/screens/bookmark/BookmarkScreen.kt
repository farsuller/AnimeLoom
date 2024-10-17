package com.solodev.animeloom.presentation.screens.bookmark

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.solodev.animeloom.presentation.navgraph.Route
import com.solodev.animeloom.presentation.screens.bookmark.components.BookmarkedAnimeCard
import com.solodev.animeloom.presentation.screens.bookmark.components.BookmarkedMangaCard


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.BookmarkScreen(
    bookmarkState: BookmarkState,
    onNavigate: (String) -> Unit,
    onAnimeClick: (String, String) -> Unit,
    onMangaClick: (String, String) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
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

        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(bookmarkState.bookMarkMangaState) { manga ->
                BookmarkedMangaCard(
                    mangaData = manga,
                    onClick = {
                        onMangaClick(
                            manga.attributes?.posterImage?.original ?: "",
                            manga.id
                        )
                    },
                    animatedVisibilityScope = animatedVisibilityScope
                )
            }
        }

        LazyColumn(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(bookmarkState.bookMarkAnimeState) { anime ->
                BookmarkedAnimeCard(
                    animeData = anime,
                    onClick = {
                        onAnimeClick(
                            anime.attributes?.posterImage?.original ?: "",
                            anime.id
                        )
                    },
                    animatedVisibilityScope = animatedVisibilityScope
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


    }
}
