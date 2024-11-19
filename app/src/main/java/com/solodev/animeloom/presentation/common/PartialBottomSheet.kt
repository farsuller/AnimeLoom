package com.solodev.animeloom.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.presentation.screens.home.components.AnimeCard
import com.solodev.animeloom.presentation.screens.home.states.AnimeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartialBottomSheet(
    onClickItem: (AnimeData) -> Unit = {},
    sheetState: SheetState,
    dismissBottomSheet: () -> Unit,
    animeBySeeAllState: AnimeState,
    animeByCategoryState: AnimeState,
    isSelectedSeeAll: Boolean = false,
    selectedSeeAllTitle: String? = null,
    selectedCategoryTitle: String? = null,
) {
    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(),
        sheetState = sheetState,
        onDismissRequest = { dismissBottomSheet() },
    ) {
        if (isSelectedSeeAll) {
            selectedSeeAllTitle?.let { seeAllTitle ->
                HeaderBar(headerTitle = HeaderTitle(text = seeAllTitle))
            }

            when {
                animeBySeeAllState.isLoading -> ShimmerEffectVerticalGrid()
                animeBySeeAllState.errorMessage != null -> {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = "Error: ${animeBySeeAllState.errorMessage}")
                    }
                }

                animeBySeeAllState.animeDataList != null -> {
                    val takeAnimeList = animeBySeeAllState.animeDataList

                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 8.dp),
                        columns = GridCells.Fixed(3),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(takeAnimeList) { anime ->
                            AnimeCard(
                                animeData = anime,
                                onClick = { onClickItem(anime) },
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        } else {
            selectedCategoryTitle?.let { categoryTitle ->
                HeaderBar(headerTitle = HeaderTitle(text = "$categoryTitle Anime"))
            }

            when {
                animeByCategoryState.isLoading -> ShimmerEffectVerticalGrid()
                animeByCategoryState.errorMessage != null -> {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = "Error: ${animeByCategoryState.errorMessage}")
                    }
                }

                animeByCategoryState.animeDataList != null -> {
                    val takeAnimeList = animeByCategoryState.animeDataList

                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 8.dp),
                        columns = GridCells.Fixed(3),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(takeAnimeList) { anime ->
                            AnimeCard(
                                animeData = anime,
                                onClick = { onClickItem(anime) },
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}
