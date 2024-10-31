package com.solodev.animeloom.presentation.screens.manga.states

import com.solodev.animeloom.domain.model.MangaData

data class MangaState(
    val manga: List<MangaData>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
