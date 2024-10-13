package com.solodev.animeloom.presentation.screens.bookmark

import com.solodev.animeloom.domain.model.AnimeData

data class BookmarkState(
    val animeState: List<AnimeData> = emptyList(),
)
