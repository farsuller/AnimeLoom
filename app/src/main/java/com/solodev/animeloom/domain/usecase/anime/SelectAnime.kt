package com.solodev.animeloom.domain.usecase.anime

import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow

class SelectAnime(
    private val animeRepository: AnimeRepository,
) {
    operator fun invoke(): Flow<List<AnimeData>> {
        return animeRepository.selectAnime()
    }
}
