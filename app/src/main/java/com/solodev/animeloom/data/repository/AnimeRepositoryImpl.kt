package com.solodev.animeloom.data.repository

import com.solodev.animeloom.data.local.AnimeDao
import com.solodev.animeloom.data.remote.AnimeApi
import com.solodev.animeloom.data.remote.dto.response.AnimeListResponse
import com.solodev.animeloom.data.remote.dto.response.AnimeResponse
import com.solodev.animeloom.data.remote.dto.response.CastingsResponse
import com.solodev.animeloom.data.remote.dto.response.CategoriesResponse
import com.solodev.animeloom.data.remote.safeApiCall
import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val apiService: AnimeApi,
    private val animeDao: AnimeDao,
) : AnimeRepository {
    override suspend fun getTrendingAnimeList(
        status: String?,
        categories: String?,
        limit: Int?,
        sort: String?,
    ): Flow<Response<AnimeListResponse>> =
        safeApiCall {
            apiService.getTrendingAnimeList(
                status = status,
                categories = categories,
                limit = limit,
                sort = sort,
            )
        }

    override suspend fun getAnimeList(
        status: String?,
        categories: String?,
        limit: Int?,
        sort: String?,
    ): Flow<Response<AnimeListResponse>> = safeApiCall {
        apiService.getAnimeList(
            status = status,
            categories = categories,
            limit = limit,
            sort = sort,
        )
    }

    override suspend fun getAnimeById(id: Int): Flow<Response<AnimeResponse>> = safeApiCall {
        apiService.getAnimeById(id = id)
    }

    override suspend fun getCastingsById(
        mediaType: String?,
        mediaId: Int?,
        isCharacter: Boolean?,
        language: String?,
        include: String?,
        sort: String?,
    ): Flow<Response<CastingsResponse>> = safeApiCall {
        apiService.getCastingsById(
            mediaType = mediaType,
            mediaId = mediaId,
            isCharacter = isCharacter,
            language = language,
            include = include,
            sort = sort,
        )
    }

    override suspend fun getCategories(limit: Int?, sort: String?): Flow<Response<CategoriesResponse>> = safeApiCall {
        apiService.getCategories(limit = limit, sort = sort)
    }

    override suspend fun upsertAnime(animeData: AnimeData) {
        animeDao.upsert(animeData = animeData)
    }

    override suspend fun deleteAnimeById(deleteAnimeById: String) {
        animeDao.deleteById(id = deleteAnimeById)
    }

    override fun selectAnime(): Flow<List<AnimeData>> {
        return animeDao.selectAnimes()
    }

    override suspend fun selectAnimeById(id: String): AnimeData? {
        return animeDao.selectAnimeById(id = id)
    }
}
