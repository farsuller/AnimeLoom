package com.solodev.animeloom.domain.usecase.manga

import com.solodev.animeloom.data.remote.dto.response.MangaListResponse
import com.solodev.animeloom.domain.repository.MangaRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetManga(
    private val repository: MangaRepository,
) {
    suspend operator fun invoke() : Flow<Response<MangaListResponse>> {
        return repository.getManga()
    }
}