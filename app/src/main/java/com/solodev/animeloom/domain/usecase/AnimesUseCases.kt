package com.solodev.animeloom.domain.usecase

import com.solodev.animeloom.domain.usecase.anime.GetAnime
import com.solodev.animeloom.domain.usecase.anime.GetAnimeId
import com.solodev.animeloom.domain.usecase.anime.GetCategories

data class AnimesUseCases (
  val getAnimes: GetAnime,
  val getAnimeId: GetAnimeId,
  val getCategories: GetCategories
)