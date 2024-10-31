package com.solodev.animeloom.presentation.screens.home

import com.solodev.animeloom.domain.model.AnimeData


data class AnimeDetailState(
    val animeDataDetail: AnimeData? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)