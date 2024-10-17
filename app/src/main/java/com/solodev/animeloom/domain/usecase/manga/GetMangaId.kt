package com.solodev.animeloom.domain.usecase.manga

import com.solodev.animeloom.data.remote.dto.response.MangaResponse
import com.solodev.animeloom.domain.repository.MangaRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetMangaId(
    private val repository: MangaRepository
) {
    suspend operator fun invoke(id: Int): Flow<Response<MangaResponse>> {
        return repository.getMangaById(id = id)
    }
}