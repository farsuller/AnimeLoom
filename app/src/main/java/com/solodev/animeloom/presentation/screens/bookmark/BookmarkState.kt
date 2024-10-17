package com.solodev.animeloom.presentation.screens.bookmark

import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.domain.model.MangaData

data class BookmarkState(
    val bookMarkAnimeState: List<AnimeData> = emptyList(),
    val bookMarkMangaState: List<MangaData> = emptyList(),
)
