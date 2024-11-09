package com.solodev.animeloom.domain.usecase.manga

import com.solodev.animeloom.domain.repository.MangaRepository

class DeleteMangaById(
    private val repository: MangaRepository,
) {
    suspend operator fun invoke(deleteMangaById: String) {
        repository.deleteMangaById(deleteMangaById = deleteMangaById)
    }
}