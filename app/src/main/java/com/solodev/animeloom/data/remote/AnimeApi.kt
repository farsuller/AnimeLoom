package com.solodev.animeloom.data.remote


import com.solodev.animeloom.data.remote.dto.response.AnimeListResponse
import com.solodev.animeloom.data.remote.dto.response.AnimeResponse
import com.solodev.animeloom.data.remote.dto.response.CategoriesResponse
import com.solodev.animeloom.data.remote.dto.response.MangaListResponse
import com.solodev.animeloom.data.remote.dto.response.MangaResponse
import com.solodev.animeloom.data.remote.dto.response.TrendingMangaListResponse
import com.solodev.animeloom.data.remote.dto.response.CastingsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface AnimeApi {
    @GET("trending/anime")
    suspend fun getTrendingAnimeList(
        @Query("filter[status]") status: String? = null,
        @Query("filter[categories]") categories: String? = null,
        @Query("page[limit]") limit: Int? = null,
        @Query("sort") sort: String? = null
    ): Response<AnimeListResponse>

    @GET("anime")
    suspend fun getAnimeList(
        @Query("filter[status]") status: String? = null,
        @Query("filter[categories]") categories: String? = null,
        @Query("page[limit]") limit: Int? = null,
        @Query("sort") sort: String? = null
    ): Response<AnimeListResponse>

    @GET("anime/{id}")
    suspend fun getAnimeById(@Path("id") id: Int): Response<AnimeResponse>

    @GET("castings")
    suspend fun getCastingsById(
        @Query("filter[media_type]") mediaType: String? = null,
        @Query("filter[media_id]") mediaId: Int? = null,
        @Query("filter[is_character]") isCharacter: Boolean? = null,
        @Query("filter[language]") language: String? = null,
        @Query("include") include: String? = null,
        @Query("sort") sort: String? = null,
    ): Response<CastingsResponse>

    @GET("categories")
    suspend fun getCategories(
        @Query("page[limit]") limit: Int? = null,
        @Query("sort") sort: String? = null
    ): Response<CategoriesResponse>

    @GET("trending/manga")
    suspend fun getTrendingMangaList(
        @Query("filter[status]") status: String? = null,
        @Query("filter[categories]") categories: String? = null,
        @Query("page[limit]") limit: Int? = null,
        @Query("sort") sort: String? = null
    ): Response<TrendingMangaListResponse>

    @GET("manga")
    suspend fun getMangaList(
        @Query("filter[status]") status: String? = null,
        @Query("filter[categories]") categories: String? = null,
        @Query("page[limit]") limit: Int? = null,
        @Query("sort") sort: String? = null
    ): Response<MangaListResponse>

    @GET("manga/{id}")
    suspend fun getMangaById(@Path("id") id: Int): Response<MangaResponse>
}