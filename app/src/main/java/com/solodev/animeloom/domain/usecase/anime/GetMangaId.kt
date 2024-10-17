package com.solodev.animeloom.domain.usecase.anime

import com.solodev.animeloom.data.remote.dto.response.MangaResponse
import com.solodev.animeloom.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetMangaId(
    private val animeRepository: AnimeRepository) {

    suspend operator fun invoke(id: Int) : Flow<Response<MangaResponse>> {
        return animeRepository.getMangaById(id = id)
    }

}