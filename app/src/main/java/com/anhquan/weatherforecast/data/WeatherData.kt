package com.anhquan.weatherforecast.data

data class WeatherData(
    var dt: Long,
    var sunrise: Long,
    var sunset: Long,
    var temp: Temp,
    var feels_like: FeelLike,
    var pressure: Int,
    var humidity: Int,
    var weather: List<Weather>,
    var speed: Float,
    var deg: Int,
    var gust: Float,
    var clouds: Int,
    var pop: Float
)

data class Temp(
    var day: Float,
    var min: Float,
    var max: Float,
    var night: Float,
    var eve: Float,
    var morn: Float
)


data class FeelLike(
    var day: Float,
    var night: Float,
    var eve: Float,
    var morn: Float
)


data class Weather(
    var id: Int,
    var main: String,
    var description: String,
    var icon: String
)

