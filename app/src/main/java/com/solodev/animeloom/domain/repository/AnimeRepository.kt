package com.solodev.animeloom.domain.repository

import com.solodev.animeloom.data.remote.dto.response.AnimeResponse
import com.solodev.animeloom.data.remote.dto.response.CategoriesResponse
import com.solodev.animeloom.data.remote.dto.response.MangaListResponse
import com.solodev.animeloom.data.remote.dto.response.AnimeListResponse
import com.solodev.animeloom.data.remote.dto.response.MangaResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AnimeRepository {
    suspend fun getTrendingAnimeList() : Flow<Response<AnimeListResponse>>

    suspend fun getAnimeById(id: Int) : Flow<Response<AnimeResponse>>

    suspend fun getCategories() : Flow<Response<CategoriesResponse>>

    suspend fun getManga() : Flow<Response<MangaListResponse>>

    suspend fun getMangaById(id: Int) : Flow<Response<MangaResponse>>

}