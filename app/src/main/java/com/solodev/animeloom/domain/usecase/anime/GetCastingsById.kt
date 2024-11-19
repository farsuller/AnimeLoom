package com.solodev.animeloom.domain.usecase.anime

import com.solodev.animeloom.data.remote.dto.response.CastingsResponse
import com.solodev.animeloom.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetCastingsById(
    private val animeRepository: AnimeRepository,
) {
    suspend operator fun invoke(
        mediaType: String? = null,
        mediaId: Int? = null,
        isCharacter: Boolean? = null,
        language: String? = null,
        include: String? = null,
        sort: String? = null,
    ): Flow<Response<CastingsResponse>> {
        return animeRepository.getCastingsById(
            mediaType = mediaType,
            mediaId = mediaId,
            isCharacter = isCharacter,
            language = language,
            include = include,
            sort = sort,
        )
    }
}
