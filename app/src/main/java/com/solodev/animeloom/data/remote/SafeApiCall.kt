package com.solodev.animeloom.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Flow<Response<T>> = flow {
    val response = apiCall()
    if (response.isSuccessful) {
        emit(response)
    } else {
        val errorResponseBody = response.errorBody()?.string() ?: "Unknown error"
        emit(Response.error(response.code(), errorResponseBody.toResponseBody(null)))
    }
}.catch { e ->
    val errorResponseBody = (e.message ?: "Unknown error").toResponseBody(null)
    emit(Response.error(500, errorResponseBody))
}.flowOn(Dispatchers.IO)
