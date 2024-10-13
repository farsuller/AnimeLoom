package com.solodev.animeloom.presentation.screens.home

import com.solodev.animeloom.data.remote.dto.AnimeDataDto
import com.solodev.animeloom.domain.model.AnimeData

data class AnimeState(
    val animeData: List<AnimeDataDto>? = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)