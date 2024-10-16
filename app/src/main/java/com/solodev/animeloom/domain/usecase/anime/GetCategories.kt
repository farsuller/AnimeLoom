package com.solodev.animeloom.domain.usecase.anime

import com.solodev.animeloom.data.remote.dto.response.CategoriesResponse
import com.solodev.animeloom.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetCategories(
    private val animeRepository: AnimeRepository,
) {
    suspend operator fun invoke() : Flow<Response<CategoriesResponse>> {
        return animeRepository.getCategories()
    }
}