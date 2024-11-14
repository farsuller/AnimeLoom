package com.solodev.animeloom.domain.usecase.manga

import com.solodev.animeloom.data.remote.dto.response.TrendingMangaListResponse
import com.solodev.animeloom.domain.repository.MangaRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetTrendingManga(
    private val repository: MangaRepository,
) {
    suspend operator fun invoke(
        status: String? = null,
        categories: String? = null,
        limit: Int? = null,
        sort: String? = null
    ): Flow<Response<TrendingMangaListResponse>> {
        return repository.getTrendingMangaList(
            status = status,
            categories = categories,
            limit = limit,
            sort = sort
        )
    }
}