package com.anhquan.weatherforecast.network

import com.anhquan.weatherforecast.data.ApiError
import com.google.gson.Gson
import retrofit2.Response
import java.net.UnknownHostException

suspend fun <T: Any?> handleRequest(requestFunc: suspend () -> Response<T>): ApiResult<T> {
    return try {
        requestFunc.invoke().createResponse()
    } catch (exception: Exception) {
        return when (exception) {
            is UnknownHostException -> {
                Failure(NetworkReturnCode.NO_INTERNET_CONNECTION, "No internet connection!")
            }
            else -> {
                Failure(NetworkReturnCode.UNKNOWN_ERROR, "Unknown error detected")
            }
        }
    }
}


fun <T> Response<T>.createResponse(): ApiResult<T> {
    return if (this.isSuccessful) {
        Success(this.body())
    } else {
        return try {
            val apiError = Gson().fromJson(this.errorBody()?.string(), ApiError::class.java)
            Failure(apiError.cod.toInt(), apiError.message)
        } catch (e: Exception) {
            Failure(this.code(), this.message())
        }
    }
}

sealed class ApiResult<T>

data class Success<T>(val response: T?) : ApiResult<T>()

data class Failure<T>(val code: Int, val errorMsg: String) : ApiResult<T>()

