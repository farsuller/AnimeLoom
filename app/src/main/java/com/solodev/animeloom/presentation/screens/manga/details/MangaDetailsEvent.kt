package com.solodev.animeloom.presentation.screens.manga.details

import com.solodev.animeloom.domain.model.MangaData

sealed class MangaDetailsEvent {
    data class UpsertDeleteManga(val mangaData: MangaData) : MangaDetailsEvent()
    data object RemoveSideEffect : MangaDetailsEvent()
}
