package com.solodev.animeloom.domain.usecase.anime

import com.solodev.animeloom.domain.repository.AnimeRepository

class DeleteAnimeById(
    private val animeRepository: AnimeRepository,
) {
    suspend operator fun invoke(deleteAnimeById: String) {
        animeRepository.deleteAnimeById(deleteAnimeById = deleteAnimeById)
    }
}
