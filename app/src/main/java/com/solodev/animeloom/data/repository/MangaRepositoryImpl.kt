package com.solodev.animeloom.data.repository

import com.solodev.animeloom.data.local.MangaDao
import com.solodev.animeloom.data.remote.AnimeApi
import com.solodev.animeloom.data.remote.dto.response.MangaListResponse
import com.solodev.animeloom.data.remote.dto.response.MangaResponse
import com.solodev.animeloom.data.remote.dto.response.TrendingMangaListResponse
import com.solodev.animeloom.data.remote.safeApiCall
import com.solodev.animeloom.domain.model.MangaData
import com.solodev.animeloom.domain.repository.MangaRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class MangaRepositoryImpl @Inject constructor(
    private val apiService: AnimeApi,
    private val mangaDao: MangaDao,
) : MangaRepository {
    override suspend fun getTrendingMangaList(
        status: String?,
        categories: String?,
        limit: Int?,
        sort: String?,
    ): Flow<Response<TrendingMangaListResponse>> = safeApiCall {
        apiService.getTrendingMangaList(
            status = status,
            categories = categories,
            limit = limit,
            sort = sort,
        )
    }

    override suspend fun getManga(
        status: String?,
        categories: String?,
        limit: Int?,
        sort: String?,
    ): Flow<Response<MangaListResponse>> = safeApiCall {
        apiService.getMangaList(
            status = status,
            categories = categories,
            limit = limit,
            sort = sort,
        )
    }

    override suspend fun getMangaById(id: Int): Flow<Response<MangaResponse>> = safeApiCall {
        apiService.getMangaById(id = id)
    }

    override suspend fun upsertManga(mangaData: MangaData) {
        mangaDao.upsert(mangaData = mangaData)
    }

    override suspend fun deleteMangaById(deleteMangaById: String) {
        mangaDao.deleteById(id = deleteMangaById)
    }

    override fun selectManga(): Flow<List<MangaData>> {
        return mangaDao.selectManga()
    }

    override suspend fun selectMangaById(id: String): MangaData? {
        return mangaDao.selectMangaById(id = id)
    }
}
