package com.solodev.animeloom.domain.repository

import com.solodev.animeloom.data.remote.dto.response.AnimeListResponse
import com.solodev.animeloom.data.remote.dto.response.AnimeResponse
import com.solodev.animeloom.data.remote.dto.response.CategoriesResponse
import com.solodev.animeloom.data.remote.dto.response.CastingsResponse
import com.solodev.animeloom.domain.model.AnimeData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AnimeRepository {
    suspend fun getTrendingAnimeList(
        status: String?,
        categories: String?,
        limit: Int?,
        sort: String?
    ): Flow<Response<AnimeListResponse>>

    suspend fun getAnimeList(
        status: String?,
        categories: String?,
        limit: Int?,
        sort: String?
    ): Flow<Response<AnimeListResponse>>

    suspend fun getAnimeById(id: Int): Flow<Response<AnimeResponse>>

    suspend fun getCastingsById(
        mediaType: String?,
        mediaId: Int?,
        isCharacter: Boolean?,
        language: String?,
        include: String?,
        sort: String?
    ): Flow<Response<CastingsResponse>>

    suspend fun getCategories(limit: Int?,
                              sort: String?): Flow<Response<CategoriesResponse>>

    suspend fun upsertAnime(animeData: AnimeData)

    suspend fun deleteAnimeById(deleteAnimeById: String)

    fun selectAnime(): Flow<List<AnimeData>>

    suspend fun selectAnimeById(id: String): AnimeData?

}