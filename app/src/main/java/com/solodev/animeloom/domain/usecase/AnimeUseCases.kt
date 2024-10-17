package com.solodev.animeloom.domain.usecase

import com.solodev.animeloom.domain.usecase.anime.DeleteAnime
import com.solodev.animeloom.domain.usecase.anime.GetAnime
import com.solodev.animeloom.domain.usecase.anime.GetAnimeId
import com.solodev.animeloom.domain.usecase.anime.GetCategories
import com.solodev.animeloom.domain.usecase.anime.SelectAnime
import com.solodev.animeloom.domain.usecase.anime.SelectAnimeById
import com.solodev.animeloom.domain.usecase.anime.UpsertAnime

data class AnimeUseCases(
    val getAnimes: GetAnime,
    val getAnimeId: GetAnimeId,
    val getCategories: GetCategories,
    val upsertAnime: UpsertAnime,
    val deleteAnime: DeleteAnime,
    val selectAnime: SelectAnime,
    val selectAnimeById: SelectAnimeById
)