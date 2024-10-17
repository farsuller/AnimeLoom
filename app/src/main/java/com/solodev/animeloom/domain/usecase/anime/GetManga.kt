package com.solodev.animeloom.domain.usecase.anime

import com.solodev.animeloom.data.remote.dto.response.MangaListResponse
import com.solodev.animeloom.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetManga(
    private val animeRepository: AnimeRepository,
) {
    suspend operator fun invoke() : Flow<Response<MangaListResponse>> {
        return animeRepository.getManga()
    }
}