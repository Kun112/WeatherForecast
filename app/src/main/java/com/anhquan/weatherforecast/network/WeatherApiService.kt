package com.anhquan.weatherforecast.network

import com.anhquan.weatherforecast.data.ServerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("forecast/daily")
    suspend fun getDailyForecast(@Query("q") cityName: String,
                                 @Query("cnt") quantity: Int,
                                 @Query("appid") appId: String,
                                 @Query("units") unit: String = "metric") : Response<ServerResponse>
}