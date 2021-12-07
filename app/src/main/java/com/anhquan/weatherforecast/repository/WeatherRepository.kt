package com.anhquan.weatherforecast.repository

import com.anhquan.weatherforecast.data.ResultWrapper
import com.anhquan.weatherforecast.data.WeatherData

interface WeatherRepository {
    suspend fun getWeatherForecastByLocation(location: String): ResultWrapper<MutableList<WeatherData>>
}