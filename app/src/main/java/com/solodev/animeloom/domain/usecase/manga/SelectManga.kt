package com.solodev.animeloom.domain.usecase.manga

import com.solodev.animeloom.domain.model.MangaData
import com.solodev.animeloom.domain.repository.MangaRepository
import kotlinx.coroutines.flow.Flow

class SelectManga(
    private val mangaRepository: MangaRepository
) {
    operator fun invoke(): Flow<List<MangaData>> {
        return mangaRepository.selectManga()
    }
}