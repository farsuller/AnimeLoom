package com.solodev.animeloom.presentation.screens.home.states

import com.solodev.animeloom.domain.model.CategoryData

data class CategoryState(
    val categories: List<CategoryData>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
