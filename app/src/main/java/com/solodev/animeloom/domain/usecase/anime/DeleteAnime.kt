package com.solodev.animeloom.domain.usecase.anime

import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.domain.repository.AnimeRepository

class DeleteAnime(
    private val animeRepository: AnimeRepository
) {
    suspend operator fun invoke(animeData: AnimeData) {
        animeRepository.deleteAnime(animeData = animeData)
    }

}