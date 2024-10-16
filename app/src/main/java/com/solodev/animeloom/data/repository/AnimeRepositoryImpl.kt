package com.solodev.animeloom.data.repository

import com.solodev.animeloom.data.remote.AnimeApi
import com.solodev.animeloom.data.remote.dto.AnimeDataDto
import com.solodev.animeloom.data.remote.dto.response.AnimeResponse
import com.solodev.animeloom.data.remote.dto.response.CategoriesResponse
import com.solodev.animeloom.data.remote.dto.response.TrendingAnimeListResponse
import com.solodev.animeloom.domain.model.AnimeData
import com.solodev.animeloom.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val apiService: AnimeApi,

) : AnimeRepository {
    override suspend fun getTrendingAnimeList(): Flow<Response<TrendingAnimeListResponse>> = flow {

        try {
            val response = apiService.getTrendingAnimeList()

            emit(Response.success(response.body()))

        }catch (e : Exception){
            val errorResponseBody = (e.message ?: "Unknown error").toResponseBody(null)
            emit(Response.error(500, errorResponseBody))
        }

    }

    override suspend fun getAnimeById(id: Int): Flow<Response<AnimeResponse>> = flow {
        try {
            val response = apiService.getAnimeById(id = id)
            emit(Response.success(response.body()))
        }catch (e : Exception){
            val errorResponseBody = (e.message ?: "Unknown error").toResponseBody(null)
            emit(Response.error(500, errorResponseBody))
        }

    }

    override suspend fun getCategories(): Flow<Response<CategoriesResponse>> = flow {
        try {
            val response = apiService.getCategories()
            emit(Response.success(response.body()))
        }catch (e : Exception){
            val errorResponseBody = (e.message ?: "Unknown error").toResponseBody(null)
            emit(Response.error(500, errorResponseBody))
        }
    }

}