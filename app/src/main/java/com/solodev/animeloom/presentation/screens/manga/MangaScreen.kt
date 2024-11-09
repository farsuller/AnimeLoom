package com.solodev.animeloom.presentation.screens.manga

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import com.solodev.animeloom.presentation.screens.manga.components.MangaCard
import com.solodev.animeloom.presentation.screens.manga.states.MangaState
import com.solodev.animeloom.utils.alasIdString
import com.solodev.animeloom.utils.aliasPosterString
import com.solodev.animeloom.utils.aliasLocalIdString


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MangaScreen(
    mangaState: MangaState,
    onNavigate: (String) -> Unit,
    onMangaClick: (aliasPosterString?, alasIdString?, aliasLocalIdString) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    val mangaList = mangaState.manga ?: emptyList()

    LaunchedEffect(Unit) {
        onNavigate(Route.MangaRoute.route)
    }

    Column(
        modifier = Modifier
            .padding(top = 24.dp, start = 8.dp, end = 8.dp)
            .statusBarsPadding()
            .fillMaxSize(),
    ) {
        Text(
            modifier = Modifier.padding(start = 8.dp, bottom = 10.dp),
            text = "Manga",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface,
        )

        when {
            mangaState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            mangaState.errorMessage != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Error: ${mangaState.errorMessage}")
                }
            }

            mangaState.manga != null ->{
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(mangaList.size){ index ->
                        val manga = mangaList[index]
                        MangaCard(
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

                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

    }
}
