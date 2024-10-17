package com.solodev.animeloom.domain.usecase.manga

import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.domain.model.MangaData
import com.solodev.animeloom.domain.repository.AnimeRepository
import com.solodev.animeloom.domain.repository.MangaRepository

class DeleteManga(
    private val mangaRepository: MangaRepository
) {
    suspend operator fun invoke(mangaData: MangaData) {
        mangaRepository.deleteManga(mangaData = mangaData)
    }

}