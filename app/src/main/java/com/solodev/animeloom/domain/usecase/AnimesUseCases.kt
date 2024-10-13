package com.solodev.animeloom.domain.usecase

import com.solodev.animeloom.domain.usecase.anime.GetAnime
import com.solodev.animeloom.domain.usecase.anime.GetAnimeId

data class AnimesUseCases (
  val getAnimes: GetAnime,
  val getAnimeId: GetAnimeId,
)