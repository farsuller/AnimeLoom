package com.solodev.animeloom.presentation.screens.home

import com.solodev.animeloom.domain.model.AnimeData


data class TrendingAnimeState(
    val trendingAnimeList: List<AnimeData>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)