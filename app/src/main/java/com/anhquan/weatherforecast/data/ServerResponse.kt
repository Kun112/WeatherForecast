package com.anhquan.weatherforecast.data

import com.google.gson.annotations.SerializedName

data class ServerResponse(var city: City, var cod: String, var message: Float, var cnt: Int, @SerializedName("list") var weatherDataList: List<WeatherData>)