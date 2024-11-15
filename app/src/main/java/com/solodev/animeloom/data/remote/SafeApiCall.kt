package com.solodev.animeloom.data.remote

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Flow<Response<T>> = flow {
    val response = apiCall()
    emit(Response.success(response.body()))
}.catch { e ->
    val errorResponseBody = (e.message ?: "Unknown error").toResponseBody(null)
    emit(Response.error(500, errorResponseBody))
}