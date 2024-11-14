package com.solodev.animeloom.domain.usecase.anime

import com.solodev.animeloom.data.remote.dto.response.AnimeListResponse
import com.solodev.animeloom.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetAnime(
    private val animeRepository: AnimeRepository,
) {
    suspend operator fun invoke(
        status: String? = null,
        categories: String? = null,
        limit: Int? = null,
        sort: String? = null
    ): Flow<Response<AnimeListResponse>> {
        return animeRepository.getAnimeList(
            status = status,
            categories = categories,
            limit = limit,
            sort = sort
        )
    }
}