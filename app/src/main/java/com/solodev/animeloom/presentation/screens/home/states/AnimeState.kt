package com.solodev.animeloom.presentation.screens.home.states

import com.solodev.animeloom.domain.model.AnimeData

data class AnimeState(
    val animeDataList: List<AnimeData>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
