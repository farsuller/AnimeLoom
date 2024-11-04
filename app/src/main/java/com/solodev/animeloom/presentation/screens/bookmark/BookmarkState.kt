package com.solodev.animeloom.presentation.screens.bookmark

import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.domain.model.MangaData

data class BookmarkState(
    val bookMarkAnimeList: List<AnimeData>? = null,
    val bookMarkMangaList: List<MangaData>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
