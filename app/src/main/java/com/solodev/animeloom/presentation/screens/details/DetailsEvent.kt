package com.solodev.animeloom.presentation.screens.details

import com.solodev.animeloom.domain.model.AnimeData


sealed class DetailsEvent {
    data class UpsertDeleteAnime(val animeData: AnimeData) : DetailsEvent()
    data object RemoveSideEffect : DetailsEvent()
}
