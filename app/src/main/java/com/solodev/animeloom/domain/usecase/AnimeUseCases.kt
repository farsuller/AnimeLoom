package com.solodev.animeloom.domain.usecase

import com.solodev.animeloom.domain.usecase.anime.DeleteAnimeById
import com.solodev.animeloom.domain.usecase.anime.GetAnime
import com.solodev.animeloom.domain.usecase.anime.GetAnimeId
import com.solodev.animeloom.domain.usecase.anime.GetCastingsById
import com.solodev.animeloom.domain.usecase.anime.GetCategories
import com.solodev.animeloom.domain.usecase.anime.SelectAnime
import com.solodev.animeloom.domain.usecase.anime.SelectAnimeById
import com.solodev.animeloom.domain.usecase.anime.UpsertAnime

data class AnimeUseCases(
    val getAnime: GetAnime,
    val getAnimeId: GetAnimeId,
    val getCastingsById: GetCastingsById,
    val getCategories: GetCategories,
    val upsertAnime: UpsertAnime,
    val deleteAnimeById: DeleteAnimeById,
    val selectAnime: SelectAnime,
    val selectAnimeById: SelectAnimeById,
)
