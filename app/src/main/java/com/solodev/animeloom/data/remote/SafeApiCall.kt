package com.solodev.animeloom.data.remote

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Flow<Response<T>> = flow {
        try {
            val response = apiCall()
            emit(Response.success(response.body()))
            println("apiCall: $response")
        } catch (e: Exception) {
            val errorResponseBody = (e.message ?: "Unknown error").toResponseBody(null)

            println("apiCall: $e")
            emit(Response.error(500, errorResponseBody))
        }
    }