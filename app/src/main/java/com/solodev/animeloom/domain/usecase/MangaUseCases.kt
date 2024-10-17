package com.solodev.animeloom.domain.usecase

import com.solodev.animeloom.domain.usecase.manga.DeleteManga
import com.solodev.animeloom.domain.usecase.manga.GetManga
import com.solodev.animeloom.domain.usecase.manga.GetMangaId
import com.solodev.animeloom.domain.usecase.manga.SelectManga
import com.solodev.animeloom.domain.usecase.manga.SelectMangaById
import com.solodev.animeloom.domain.usecase.manga.UpsertManga

data class MangaUseCases(
    val getManga: GetManga,
    val getMangaId: GetMangaId,
    val upsertManga: UpsertManga,
    val deleteManga: DeleteManga,
    val selectManga: SelectManga,
    val selectMangaById: SelectMangaById
)