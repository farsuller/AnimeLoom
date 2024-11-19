package com.solodev.animeloom.domain.usecase.manga

import com.solodev.animeloom.domain.model.MangaData
import com.solodev.animeloom.domain.repository.MangaRepository

class UpsertManga(
    private val mangaRepository: MangaRepository,
) {
    suspend operator fun invoke(mangaData: MangaData) {
        mangaRepository.upsertManga(mangaData = mangaData)
    }
}
