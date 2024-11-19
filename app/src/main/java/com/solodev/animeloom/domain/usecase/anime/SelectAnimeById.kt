package com.solodev.animeloom.domain.usecase.anime

import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.domain.repository.AnimeRepository

class SelectAnimeById(
    private val animeRepository: AnimeRepository,
) {
    suspend operator fun invoke(id: String): AnimeData? {
        return animeRepository.selectAnimeById(id)
    }
}
