package com.solodev.animeloom.presentation.screens.home

import com.solodev.animeloom.data.remote.dto.AnimeDataDto


data class AnimeState(
    val animeData: List<AnimeDataDto>? = emptyList(),
    val animeDataDetail: AnimeDataDto? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)