package com.anhquan.weatherforecast.data

data class City(
    var id: Long,
    var name: String,
    var coord: Coordinator,
    var country: String,
    var population: Int,
    var timezone: Int
)

data class Coordinator(var lon: Float, var lat: Float)
