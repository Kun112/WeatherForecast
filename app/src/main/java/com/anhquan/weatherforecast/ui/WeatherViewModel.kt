package com.anhquan.weatherforecast.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anhquan.weatherforecast.MainApplication
import com.anhquan.weatherforecast.R
import com.anhquan.weatherforecast.data.ResultWrapper
import com.anhquan.weatherforecast.data.WeatherData
import com.anhquan.weatherforecast.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _resultWrapper = MutableLiveData<ResultWrapper<MutableList<WeatherData>>>()
    val resultWrapper: LiveData<ResultWrapper<MutableList<WeatherData>>>
        get() = _resultWrapper

    fun getWeatherData(inputLocation: String) = viewModelScope.launch {
        if (inputLocation.length >= MIN_INPUT_LENGTH) {
            _isLoading.postValue(true)
            val response = withContext(Dispatchers.IO) {
                weatherRepository.getWeatherForecastByLocation(inputLocation)
            }
            _isLoading.postValue(false)
            _resultWrapper.postValue(response)
        } else {
            _resultWrapper.postValue(ResultWrapper(MainApplication.instance.getString(R.string.invalid_length)))
        }
    }

    companion object {
        private val TAG = WeatherViewModel::class.java.simpleName
        private const val MIN_INPUT_LENGTH = 3
    }

}