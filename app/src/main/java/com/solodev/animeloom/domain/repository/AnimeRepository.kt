package com.solodev.animeloom.domain.repository

import com.solodev.animeloom.data.remote.dto.response.AnimeListResponse
import com.solodev.animeloom.data.remote.dto.response.AnimeResponse
import com.solodev.animeloom.data.remote.dto.response.CategoriesResponse
import com.solodev.animeloom.data.remote.dto.response.TrendingAnimeListResponse
import com.solodev.animeloom.domain.model.AnimeData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AnimeRepository {
    suspend fun getTrendingAnimeList() : Flow<Response<TrendingAnimeListResponse>>

    suspend fun getAnimeList() : Flow<Response<AnimeListResponse>>

    suspend fun getAnimeById(id: Int) : Flow<Response<AnimeResponse>>

    suspend fun getCategories() : Flow<Response<CategoriesResponse>>

    suspend fun upsertAnime(animeData: AnimeData)

    suspend fun deleteAnime(animeData: AnimeData)

    fun selectAnime(): Flow<List<AnimeData>>

    suspend fun selectAnimeById(id: String): AnimeData?

}