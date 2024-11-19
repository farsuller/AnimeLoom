package com.solodev.animeloom.domain.usecase

import com.solodev.animeloom.domain.usecase.manga.DeleteMangaById
import com.solodev.animeloom.domain.usecase.manga.GetManga
import com.solodev.animeloom.domain.usecase.manga.GetMangaId
import com.solodev.animeloom.domain.usecase.manga.GetTrendingManga
import com.solodev.animeloom.domain.usecase.manga.SelectManga
import com.solodev.animeloom.domain.usecase.manga.SelectMangaById
import com.solodev.animeloom.domain.usecase.manga.UpsertManga

data class MangaUseCases(
    val getTrendingManga: GetTrendingManga,
    val getManga: GetManga,
    val getMangaId: GetMangaId,
    val upsertManga: UpsertManga,
    val deleteMangaById: DeleteMangaById,
    val selectManga: SelectManga,
    val selectMangaById: SelectMangaById,
)
