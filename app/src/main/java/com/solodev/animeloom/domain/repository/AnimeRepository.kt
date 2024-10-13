package com.solodev.animeloom.domain.repository

import com.solodev.animeloom.data.remote.dto.AnimeDataDto
import com.solodev.animeloom.data.remote.dto.response.AnimeResponse
import com.solodev.animeloom.data.remote.dto.response.TrendingAnimeListResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AnimeRepository {
    suspend fun getTrendingAnimeList() : Flow<Response<TrendingAnimeListResponse>>
    suspend fun getAnimeById(id: Int) : Flow<Response<AnimeResponse>>

}