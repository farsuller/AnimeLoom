package com.solodev.animeloom.domain.usecase.anime

import com.solodev.animeloom.data.remote.dto.response.CategoriesResponse
import com.solodev.animeloom.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetCategories(
    private val animeRepository: AnimeRepository,
) {
    suspend operator fun invoke(limit: Int?,
                                sort: String?) : Flow<Response<CategoriesResponse>> {
        return animeRepository.getCategories(limit = limit, sort = sort)
    }
}