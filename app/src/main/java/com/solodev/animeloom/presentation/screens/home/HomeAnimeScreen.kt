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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.solodev.animeloom.domain.model.CategoryData
import com.solodev.animeloom.presentation.components.HeaderBar
import com.solodev.animeloom.presentation.components.HeaderShimmerEffect
import com.solodev.animeloom.presentation.components.HeaderTitle
import com.solodev.animeloom.presentation.components.PartialBottomSheet
import com.solodev.animeloom.presentation.components.SeeAll
import com.solodev.animeloom.presentation.components.ShimmerEffectCarouselWithHeader
import com.solodev.animeloom.presentation.components.ShimmerEffectCategoryCarousel
import com.solodev.animeloom.presentation.navgraph.Route
import com.solodev.animeloom.presentation.screens.home.components.AnimeCard
import com.solodev.animeloom.presentation.screens.home.components.AnimeCategoryChips
import com.solodev.animeloom.presentation.screens.home.components.HomeHeader
import com.solodev.animeloom.presentation.screens.home.states.AnimeState
import com.solodev.animeloom.presentation.screens.home.states.CategoryState
import com.solodev.animeloom.utils.alasIdString
import com.solodev.animeloom.utils.aliasLocalIdString
import com.solodev.animeloom.utils.aliasPosterString
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalSharedTransitionApi::class,
    ExperimentalMaterial3Api::class,
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SharedTransitionScope.HomeAnimesScreen(
    upcomingAnimeState: AnimeState,
    animeHighRateState: AnimeState,
    animeNewReleaseState: AnimeState,
    trendingAnimeState: AnimeState,
    animeByCategoryState: AnimeState,
    animeBySeeAllState: AnimeState,
    categoryState: CategoryState,
    isLoadingData: Boolean,
    onAnimeClick: (aliasPosterString?, alasIdString?, aliasLocalIdString) -> Unit,
    onNavigate: (String) -> Unit,
    onPullRefresh: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    selectedCategory: (CategoryData?) -> Unit = {},
    selectedSeeAll: (String?) -> Unit = {},
) {
    var selectedSeeAllAnime by remember { mutableStateOf("Trending Anime") }
    var selectedCategoryAnime by remember { mutableStateOf("Romance") }

    var showBottomSheet by remember { mutableStateOf(false) }
    var isSeeAllSelected by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

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
            .fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .pullRefresh(pullRefreshState),
        ) {
            if (isLoadingData) {
                CircularProgressIndicator()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    item {
                        when {
                            upcomingAnimeState.isLoading -> HeaderShimmerEffect()
                            upcomingAnimeState.errorMessage != null -> ErrorContentMessage(
                                animeState = upcomingAnimeState,
                            )

                            upcomingAnimeState.animeDataList != null -> {
                                HomeHeader(
                                    modifier = Modifier,
                                    animeData = upcomingAnimeState.animeDataList.getOrNull(1),
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }

                    item {
                        when {
                            categoryState.isLoading -> ShimmerEffectCategoryCarousel()
                            categoryState.errorMessage != null -> ErrorContentMessage(categoryState = categoryState)
                            categoryState.categories != null -> {
                                AnimeCategoryChips(
                                    modifier = Modifier,
                                    categoryState = categoryState,
                                    onClickCategory = { category ->
                                        showBottomSheet = true
                                        isSeeAllSelected = false
                                        selectedCategory(category)
                                        selectedCategoryAnime = category?.attributes?.title ?: ""
                                    },
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }
                    item {
                        when {
                            trendingAnimeState.isLoading -> ShimmerEffectCarouselWithHeader()
                            trendingAnimeState.errorMessage != null -> ErrorContentMessage(animeState = trendingAnimeState)

                            trendingAnimeState.animeDataList != null -> {
                                val takeAnimeList = trendingAnimeState.animeDataList.take(7)
                                val trendingAnimeText = "Popular Anime"

                                HeaderBar(
                                    headerTitle = HeaderTitle(text = trendingAnimeText),
                                    seeAll = SeeAll(seeAllClicked = {
                                        showBottomSheet = true
                                        selectedSeeAll(trendingAnimeText)
                                        selectedSeeAllAnime = trendingAnimeText
                                        isSeeAllSelected = true
                                    }),
                                )

                                LazyRow(
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                ) {
                                    items(takeAnimeList) { anime ->
                                        AnimeCard(
                                            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                                            animeData = anime,
                                            onClick = {
                                                onAnimeClick(
                                                    anime.attributes?.posterImage?.original ?: "",
                                                    anime.id,
                                                    anime.localId,
                                                )
                                            },
                                            animatedVisibilityScope = animatedVisibilityScope,
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                        }
                    }

                    item {
                        when {
                            upcomingAnimeState.isLoading -> ShimmerEffectCarouselWithHeader()
                            upcomingAnimeState.errorMessage != null -> ErrorContentMessage(animeState = upcomingAnimeState)

                            upcomingAnimeState.animeDataList != null -> {
                                val takeAnimeList = upcomingAnimeState.animeDataList.take(7)
                                val upcomingAnimeText = "Upcoming Anime"

                                HeaderBar(
                                    headerTitle = HeaderTitle(text = upcomingAnimeText),
                                    seeAll = SeeAll(seeAllClicked = {
                                        showBottomSheet = true
                                        isSeeAllSelected = true
                                        selectedSeeAll(upcomingAnimeText)
                                        selectedSeeAllAnime = upcomingAnimeText
                                    }),
                                )

                                LazyRow(
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                ) {
                                    items(takeAnimeList) { anime ->
                                        AnimeCard(
                                            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                                            animeData = anime,
                                            onClick = {
                                                onAnimeClick(
                                                    anime.attributes?.posterImage?.original ?: "",
                                                    anime.id,
                                                    anime.localId,
                                                )
                                            },
                                            animatedVisibilityScope = animatedVisibilityScope,
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                        }
                    }

                    item {
                        when {
                            animeHighRateState.isLoading -> ShimmerEffectCarouselWithHeader()
                            animeHighRateState.errorMessage != null -> ErrorContentMessage(animeState = animeHighRateState)

                            animeHighRateState.animeDataList != null -> {
                                val takeAnimeList = animeHighRateState.animeDataList.take(7)
                                val highestAnimeText = "Highest Rated Anime"

                                HeaderBar(
                                    headerTitle = HeaderTitle(text = highestAnimeText),
                                    seeAll = SeeAll(seeAllClicked = {
                                        showBottomSheet = true
                                        isSeeAllSelected = true
                                        selectedSeeAll(highestAnimeText)
                                        selectedSeeAllAnime = highestAnimeText
                                    }),
                                )

                                LazyRow(
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                ) {
                                    items(takeAnimeList) { anime ->
                                        AnimeCard(
                                            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                                            animeData = anime,
                                            onClick = {
                                                onAnimeClick(
                                                    anime.attributes?.posterImage?.original ?: "",
                                                    anime.id,
                                                    anime.localId,
                                                )
                                            },
                                            animatedVisibilityScope = animatedVisibilityScope,
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                        }
                    }

                    item {
                        when {
                            animeNewReleaseState.isLoading -> ShimmerEffectCarouselWithHeader()
                            animeNewReleaseState.errorMessage != null -> ErrorContentMessage(animeState = animeNewReleaseState)

                            animeNewReleaseState.animeDataList != null -> {
                                val takeAnimeList = animeNewReleaseState.animeDataList.take(7)
                                val romanceAnimeText = "Newly Released Anime"

                                HeaderBar(
                                    headerTitle = HeaderTitle(text = romanceAnimeText),
                                    seeAll = SeeAll(seeAllClicked = {
                                        showBottomSheet = true
                                        isSeeAllSelected = true
                                        selectedSeeAll(romanceAnimeText)
                                        selectedSeeAllAnime = romanceAnimeText
                                    }),
                                )

                                LazyRow(
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                ) {
                                    items(takeAnimeList) { anime ->
                                        AnimeCard(
                                            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                                            animeData = anime,
                                            onClick = {
                                                onAnimeClick(
                                                    anime.attributes?.posterImage?.original ?: "",
                                                    anime.id,
                                                    anime.localId,
                                                )
                                            },
                                            animatedVisibilityScope = animatedVisibilityScope,
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                        }
                    }
                }
            }
            if (showBottomSheet) {
                PartialBottomSheet(
                    sheetState = sheetState,
                    dismissBottomSheet = { showBottomSheet = false },
                    onClickItem = { anime ->
                        onAnimeClick(
                            anime.attributes?.posterImage?.original ?: "",
                            anime.id,
                            anime.localId,
                        )
                    },
                    selectedSeeAllTitle = selectedSeeAllAnime,
                    selectedCategoryTitle = selectedCategoryAnime,
                    isSelectedSeeAll = isSeeAllSelected,
                    animeByCategoryState = animeByCategoryState,
                    animeBySeeAllState = animeBySeeAllState,
                )
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

@Composable
private fun ErrorContentMessage(
    animeState: AnimeState? = null,
    categoryState: CategoryState? = null,
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        if (animeState != null) {
            Text(text = "Error: ${animeState.errorMessage}")
        }

        if (categoryState != null) {
            Text(text = "Error: ${categoryState.errorMessage}")
        }
    }
}
