package com.solodev.animeloom.presentation.screens.manga

import com.solodev.animeloom.domain.model.MangaData

data class MangaDetailState(
    val mangaDataDetail: MangaData? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
