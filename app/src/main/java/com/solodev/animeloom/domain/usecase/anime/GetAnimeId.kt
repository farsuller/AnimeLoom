package com.solodev.animeloom.domain.usecase.anime

import com.solodev.animeloom.data.remote.dto.response.AnimeResponse
import com.solodev.animeloom.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetAnimeId(
    private val animeRepository: AnimeRepository,
) {
    suspend operator fun invoke(id: Int): Flow<Response<AnimeResponse>> {
        return animeRepository.getAnimeById(id = id)
    }
}
