package com.solodev.animeloom.presentation.common

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.presentation.screens.home.components.AnimeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartialBottomSheet(
    onClickItem: (AnimeData) -> Unit = {},
    sheetState: SheetState,
    dismissBottomSheet: () -> Unit,
    trendingAnimeList: List<AnimeData>? = null,
    upcomingAnimeList: List<AnimeData>? = null,
    highestRatedAnimeList: List<AnimeData>? = null,
    animeRomanceAnimeList: List<AnimeData>? = null,
    selectedSeeAll: String = "Trending Anime",
) {

    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(),
        sheetState = sheetState,
        onDismissRequest = { dismissBottomSheet() }
    ) {


        HeaderBar(headerTitle = HeaderTitle(text = selectedSeeAll))

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            when (selectedSeeAll) {
                "Trending Anime" -> {
                    trendingAnimeList?.let {
                        items(trendingAnimeList) { anime ->
                            AnimeCard(
                                animeData = anime,
                                onClick = { onClickItem(anime) },
                            )
                        }
                    }
                }

                "Upcoming Anime" -> {
                    upcomingAnimeList?.let {
                        items(upcomingAnimeList) { anime ->
                            AnimeCard(
                                animeData = anime,
                                onClick = { onClickItem(anime) },
                            )
                        }
                    }
                }

                "Highest Rated Anime" -> {
                    highestRatedAnimeList?.let {
                        items(highestRatedAnimeList) { anime ->
                            AnimeCard(
                                animeData = anime,
                                onClick = { onClickItem(anime) },
                            )
                        }
                    }
                }

                "Romance Anime" -> {
                    animeRomanceAnimeList?.let {
                        items(animeRomanceAnimeList) { anime ->
                            AnimeCard(
                                animeData = anime,
                                onClick = { onClickItem(anime) },
                            )
                        }
                    }
                }
            }

        }
        Spacer(modifier = Modifier.height(20.dp))
    }


}