package com.solodev.animeloom.presentation.screens.manga

import com.solodev.animeloom.domain.model.CategoryData

data class MangaState(
    val manga: List<CategoryData>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
