package com.solodev.animeloom.domain.repository

import com.solodev.animeloom.data.remote.dto.response.MangaListResponse
import com.solodev.animeloom.data.remote.dto.response.MangaResponse
import com.solodev.animeloom.data.remote.dto.response.TrendingMangaListResponse
import com.solodev.animeloom.domain.model.MangaData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MangaRepository {

    suspend fun getTrendingMangaList(
        status: String?,
        categories: String?,
        limit: Int?,
        sort: String?,
    ): Flow<Response<TrendingMangaListResponse>>

    suspend fun getManga(
        status: String?,
        categories: String?,
        limit: Int?,
        sort: String?,
    ): Flow<Response<MangaListResponse>>

    suspend fun getMangaById(id: Int): Flow<Response<MangaResponse>>

    suspend fun upsertManga(mangaData: MangaData)

    suspend fun deleteMangaById(deleteMangaById: String)

    fun selectManga(): Flow<List<MangaData>>

    suspend fun selectMangaById(id: String): MangaData?
}
