package com.anhquan.weatherforecast.repository

import com.anhquan.weatherforecast.data.ServerResponse
import com.anhquan.weatherforecast.network.ApiResult

interface RemoteDataSource {
    suspend fun getWeatherForecastByLocation(
        location: String,
        forecastDay: Int,
        appId: String
    ): ApiResult<ServerResponse>
}