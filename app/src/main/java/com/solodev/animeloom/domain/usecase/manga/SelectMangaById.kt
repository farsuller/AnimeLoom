package com.solodev.animeloom.domain.usecase.manga

import com.solodev.animeloom.domain.model.MangaData
import com.solodev.animeloom.domain.repository.MangaRepository

class SelectMangaById(
    private val mangaRepository: MangaRepository,
) {
    suspend operator fun invoke(id: String): MangaData? {
        return mangaRepository.selectMangaById(id)
    }
}
