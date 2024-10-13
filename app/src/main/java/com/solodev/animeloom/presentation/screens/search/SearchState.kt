package com.solodev.animeloom.presentation.screens.search

import androidx.paging.PagingData
import com.solodev.animeloom.domain.model.AnimeData

import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery: String = "",
    val animes: List<AnimeData>? = null,
)
