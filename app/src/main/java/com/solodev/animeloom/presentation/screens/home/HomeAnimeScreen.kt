package com.solodev.animeloom.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.solodev.animeloom.presentation.common.HeaderBar
import com.solodev.animeloom.presentation.common.HeaderShimmerEffect
import com.solodev.animeloom.presentation.common.ShimmerEffectCarouselWithHeader
import com.solodev.animeloom.presentation.common.ShimmerEffectCategoryCarousel
import com.solodev.animeloom.presentation.navgraph.Route
import com.solodev.animeloom.presentation.screens.bookmark.BookmarkState
import com.solodev.animeloom.presentation.screens.home.components.AnimeCard
import com.solodev.animeloom.presentation.screens.home.components.AnimeCategoryChips
import com.solodev.animeloom.presentation.common.HeaderTitle
import com.solodev.animeloom.presentation.common.SeeAll
import com.solodev.animeloom.presentation.screens.home.components.HomeHeader
import com.solodev.animeloom.presentation.screens.home.components.HomeMangaCard
import com.solodev.animeloom.presentation.screens.home.states.AnimeState
import com.solodev.animeloom.presentation.screens.home.states.CategoryState
import com.solodev.animeloom.presentation.screens.home.states.TrendingAnimeState
import com.solodev.animeloom.presentation.screens.manga.states.MangaState
import com.solodev.animeloom.presentation.screens.manga.states.TrendingMangaState
import com.solodev.animeloom.utils.alasIdString
import com.solodev.animeloom.utils.aliasPosterString
import com.solodev.animeloom.utils.aliasLocalIdString
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalSharedTransitionApi::class,
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SharedTransitionScope.HomeAnimesScreen(
    animeState: AnimeState,
    trendingAnimeState: TrendingAnimeState,
    mangaState: MangaState,
    trendingMangaState: TrendingMangaState,
    bookmarkState: BookmarkState,
    categoryState: CategoryState,
    isLoadingData : Boolean,
    onAnimeClick: (aliasPosterString?, alasIdString?, aliasLocalIdString) -> Unit,
    onMangaClick: (aliasPosterString?, alasIdString?, aliasLocalIdString) -> Unit,
    onNavigate: (String) -> Unit,
    onPullRefresh: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    var isRefreshing by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            scope.launch {
                isRefreshing = true
                delay(500)
                onPullRefresh()
                isRefreshing = false
            }
        },
    )

    LaunchedEffect(Unit) {
        onNavigate(Route.HomeRoute.route)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .pullRefresh(pullRefreshState)
        ) {

            if (isLoadingData) {
                CircularProgressIndicator()
            }
            else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {

                    item {
                        when {
                            trendingAnimeState.isLoading -> HeaderShimmerEffect()
                            trendingAnimeState.errorMessage != null -> {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "Error: ${animeState.errorMessage}")
                                }
                            }

                            trendingAnimeState.trendingAnimeList != null -> {
                                HomeHeader(
                                    modifier = Modifier,
                                    animeData = trendingAnimeState.trendingAnimeList.firstOrNull()
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }

                    item {
                        when {
                            categoryState.isLoading -> ShimmerEffectCategoryCarousel()
                            categoryState.errorMessage != null -> {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "Error: ${animeState.errorMessage}")
                                }
                            }

                            categoryState.categories != null -> {
                                AnimeCategoryChips(
                                    modifier = Modifier,
                                    categoryState = categoryState
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }

                    }
                    item {
                        when {
                            trendingAnimeState.isLoading -> ShimmerEffectCarouselWithHeader()
                            trendingAnimeState.errorMessage != null -> {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "Error: ${animeState.errorMessage}")
                                }
                            }

                            trendingAnimeState.trendingAnimeList != null -> {
                                val takeAnimeList = trendingAnimeState.trendingAnimeList.take(7)

                                HeaderBar(
                                    headerTitle = HeaderTitle(text = "Trending Anime"),
                                    seeAll = SeeAll(text = "See All", headerText = "See All Trending Anime"))

                                LazyRow(
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    items(takeAnimeList) { anime ->
                                        AnimeCard(
                                            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
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
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                        }
                    }

                    item {
                        when {
                            trendingMangaState.isLoading -> ShimmerEffectCarouselWithHeader()
                            trendingMangaState.errorMessage != null -> {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "Error: ${animeState.errorMessage}")
                                }
                            }

                            trendingMangaState.trendingMangaList != null -> {
                                val filterRank = trendingMangaState.trendingMangaList.filter { f -> f.attributes?.ratingRank != null && f.attributes.ratingRank < 5000 }

                                HeaderBar(
                                    headerTitle = HeaderTitle(text = "Trending Manga"),
                                    seeAll = SeeAll(text = "See All", headerText = "See All Trending Manga"))

                                LazyRow(
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    items(filterRank) { manga ->
                                        HomeMangaCard(
                                            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
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
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                        }
                    }

                    item {
                        when {
                            animeState.isLoading -> ShimmerEffectCarouselWithHeader()
                            animeState.errorMessage != null -> {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "Error: ${animeState.errorMessage}")
                                }
                            }

                            animeState.animeDataList != null -> {
                                val takeAnimeList = animeState.animeDataList.take(7)

                                HeaderBar(
                                    headerTitle = HeaderTitle(text = "Anime"),
                                    seeAll = SeeAll(text = "See All", headerText = "See All Anime"))

                                LazyRow(
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    items(takeAnimeList) { anime ->
                                        AnimeCard(
                                            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
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
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                        }
                    }
                }
            }


            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier
                    .zIndex(1f)
                    .align(Alignment.TopCenter),
            )
        }
    }
}





