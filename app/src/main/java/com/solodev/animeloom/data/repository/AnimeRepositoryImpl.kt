package com.solodev.animeloom.data.repository

import com.solodev.animeloom.data.local.AnimeDao
import com.solodev.animeloom.data.remote.AnimeApi
import com.solodev.animeloom.data.remote.dto.response.AnimeListResponse
import com.solodev.animeloom.data.remote.dto.response.AnimeResponse
import com.solodev.animeloom.data.remote.dto.response.CategoriesResponse
import com.solodev.animeloom.data.remote.dto.response.TrendingAnimeListResponse
import com.solodev.animeloom.data.remote.safeApiCall
import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val apiService: AnimeApi,
    private val animeDao: AnimeDao
    ) : AnimeRepository {
    override suspend fun getTrendingAnimeList(): Flow<Response<TrendingAnimeListResponse>> = safeApiCall {
        apiService.getTrendingAnimeList()
    }

    override suspend fun getAnimeList(): Flow<Response<AnimeListResponse>> = safeApiCall {
        apiService.getAnimeList()
    }

    override suspend fun getAnimeById(id: Int): Flow<Response<AnimeResponse>> = safeApiCall {
        apiService.getAnimeById(id = id)
    }

    override suspend fun getCategories(): Flow<Response<CategoriesResponse>> = safeApiCall {
        apiService.getCategories()
    }

    override suspend fun upsertAnime(animeData: AnimeData) {
        animeDao.upsert(animeData = animeData)
    }

    override suspend fun deleteAnime(animeData: AnimeData) {
        animeDao.delete(animeData = animeData)
    }

    override fun selectAnime(): Flow<List<AnimeData>> {
        return animeDao.selectAnimes()
    }

    override suspend fun selectAnimeById(id: String): AnimeData? {
        return animeDao.selectAnimeById(id = id)
    }
}