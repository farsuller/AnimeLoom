package com.solodev.animeloom.domain.usecase.anime

import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.domain.repository.AnimeRepository

class UpsertAnime(
    private val animeRepository: AnimeRepository
) {
    suspend operator fun invoke(animeData: AnimeData){
        animeRepository.upsertAnime(animeData = animeData)
    }
}