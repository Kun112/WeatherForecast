package com.anhquan.weatherforecast.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anhquan.weatherforecast.repository.WeatherRepository
import com.anhquan.weatherforecast.ui.WeatherViewModel

class ViewModelFactory(private val weatherRepository: WeatherRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(weatherRepository) as T
        } else {
            throw IllegalArgumentException("Unknown class name")
        }
    }
}