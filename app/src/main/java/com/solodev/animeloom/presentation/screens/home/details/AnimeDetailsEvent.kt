package com.solodev.animeloom.presentation.screens.home.details

import com.solodev.animeloom.domain.model.AnimeData

sealed class AnimeDetailsEvent {
    data class UpsertDeleteAnime(val animeData: AnimeData) : AnimeDetailsEvent()
    data object RemoveSideEffect : AnimeDetailsEvent()
}
