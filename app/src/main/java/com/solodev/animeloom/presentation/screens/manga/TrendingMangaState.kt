package com.solodev.animeloom.presentation.screens.manga

import com.solodev.animeloom.domain.model.MangaData

data class TrendingMangaState(
    val trendingMangaList: List<MangaData>? = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
