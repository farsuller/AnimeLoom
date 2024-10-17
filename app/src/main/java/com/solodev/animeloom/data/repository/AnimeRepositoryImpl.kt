package com.solodev.animeloom.data.repository

import com.solodev.animeloom.data.remote.AnimeApi
import com.solodev.animeloom.data.remote.dto.response.AnimeResponse
import com.solodev.animeloom.data.remote.dto.response.CategoriesResponse
import com.solodev.animeloom.data.remote.dto.response.MangaListResponse
import com.solodev.animeloom.data.remote.dto.response.AnimeListResponse
import com.solodev.animeloom.data.remote.dto.response.MangaResponse
import com.solodev.animeloom.data.remote.safeApiCall
import com.solodev.animeloom.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val apiService: AnimeApi,

    ) : AnimeRepository {
    override suspend fun getTrendingAnimeList(): Flow<Response<AnimeListResponse>> = safeApiCall {
        apiService.getTrendingAnimeList()
    }

    override suspend fun getAnimeById(id: Int): Flow<Response<AnimeResponse>> = safeApiCall {
        apiService.getAnimeById(id = id)
    }

    override suspend fun getCategories(): Flow<Response<CategoriesResponse>> = safeApiCall {
        apiService.getCategories()
    }

    override suspend fun getManga(): Flow<Response<MangaListResponse>> = safeApiCall {
        apiService.getManga()
    }

    override suspend fun getMangaById(id: Int): Flow<Response<MangaResponse>> = safeApiCall {
        apiService.getMangaById(id = id)
    }

}