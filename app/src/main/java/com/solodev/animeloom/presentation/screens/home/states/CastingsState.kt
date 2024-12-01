package com.solodev.animeloom.presentation.screens.home.states

import com.solodev.animeloom.domain.model.CastingsData

data class CastingsState(
    val castingDataList: List<CastingsData>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
