package com.solodev.animeloom.domain.usecase.manga

import com.solodev.animeloom.data.remote.dto.response.TrendingMangaListResponse
import com.solodev.animeloom.domain.repository.MangaRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetTrendingManga(
    private val repository: MangaRepository,
) {
    suspend operator fun invoke(): Flow<Response<TrendingMangaListResponse>> {
        return repository.getTrendingMangaList()
    }
}