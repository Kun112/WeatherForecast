package com.anhquan.weatherforecast.repository

import android.content.Context
import com.anhquan.weatherforecast.data.ServerResponse
import com.anhquan.weatherforecast.network.ApiResult
import com.anhquan.weatherforecast.network.ServiceGenerator
import com.anhquan.weatherforecast.network.handleRequest

class RemoteDataSourceImpl(context: Context): RemoteDataSource {
    private val weatherApiService = ServiceGenerator.provideWeatherApiService(context)

    override suspend fun getWeatherForecastByLocation(location: String, forecastDay: Int, appId: String): ApiResult<ServerResponse> {
        return handleRequest { weatherApiService.getDailyForecast(location, forecastDay, appId) }
    }

}