package com.solodev.animeloom.domain.usecase

import com.solodev.animeloom.domain.usecase.anime.GetAnime
import com.solodev.animeloom.domain.usecase.anime.GetAnimeId
import com.solodev.animeloom.domain.usecase.anime.GetCategories
import com.solodev.animeloom.domain.usecase.anime.GetManga
import com.solodev.animeloom.domain.usecase.anime.GetMangaId

data class AnimesUseCases (
  val getAnimes: GetAnime,
  val getAnimeId: GetAnimeId,
  val getCategories: GetCategories,
  val getManga: GetManga,
  val getMangaId: GetMangaId,

  )