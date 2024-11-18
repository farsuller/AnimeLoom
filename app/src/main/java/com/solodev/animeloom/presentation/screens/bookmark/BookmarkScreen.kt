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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.solodev.animeloom.presentation.common.EmptyBookmarked
import com.solodev.animeloom.presentation.common.HeaderBar
import com.solodev.animeloom.presentation.common.HeaderTitle
import com.solodev.animeloom.presentation.navgraph.Route
import com.solodev.animeloom.presentation.screens.bookmark.components.BookmarkedAnimeCard
import com.solodev.animeloom.presentation.screens.bookmark.components.BookmarkedMangaCard
import com.solodev.animeloom.utils.alasIdString
import com.solodev.animeloom.utils.aliasLocalIdString
import com.solodev.animeloom.utils.aliasPosterString


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.BookmarkScreen(
    bookmarkState: BookmarkState,
    onNavigate: (String) -> Unit,
    isLoadingData: Boolean,
    onAnimeClick: (aliasPosterString, alasIdString, aliasLocalIdString) -> Unit,
    onMangaClick: (aliasPosterString, alasIdString, aliasLocalIdString) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    LaunchedEffect(Unit) {
        onNavigate(Route.BookmarkRoute.route)
    }

    if (isLoadingData) CircularProgressIndicator()
    else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(top = 24.dp),
        ) {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = "Bookmark",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(modifier = Modifier.height(10.dp))

            HeaderBar(headerTitle = HeaderTitle(text = "Manga"))
            if (bookmarkState.bookMarkMangaList?.isEmpty() == true || bookmarkState.bookMarkMangaList == null) {
                EmptyBookmarked(
                    message = "Bookmark your Favorite Manga"
                )
            } else {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(bookmarkState.bookMarkMangaList) { manga ->
                        BookmarkedMangaCard(
                            mangaData = manga,
                            onClick = {
                                onMangaClick(
                                    manga.attributes?.posterImage?.original ?: "",
                                    manga.id,
                                    manga.localId
                                )
                            },
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            HeaderBar(headerTitle = HeaderTitle(text = "Anime"))

            if (bookmarkState.bookMarkAnimeList?.isEmpty() == true || bookmarkState.bookMarkAnimeList == null) {
                EmptyBookmarked(
                    message = "Bookmark your Favorite Anime",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {

                    items(bookmarkState.bookMarkAnimeList) { anime ->
                        BookmarkedAnimeCard(
                            animeData = anime,
                            onClick = {
                                onAnimeClick(
                                    anime.attributes?.posterImage?.original ?: "",
                                    anime.id,
                                    anime.localId
                                )
                            },
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

}
