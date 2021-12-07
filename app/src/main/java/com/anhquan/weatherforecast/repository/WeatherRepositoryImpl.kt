package com.anhquan.weatherforecast.repository

import android.content.Context
import com.anhquan.weatherforecast.data.ResultWrapper
import com.anhquan.weatherforecast.data.WeatherData
import com.anhquan.weatherforecast.network.Failure
import com.anhquan.weatherforecast.network.Success
import com.anhquan.weatherforecast.utils.Constants
import com.anhquan.weatherforecast.utils.KeyUtils


class WeatherRepositoryImpl (private val localDataSourceImpl: LocalDataSource, private val remoteDataSourceImpl: RemoteDataSource): WeatherRepository {

    private val appId = KeyUtils.getAppId()

    override suspend fun getWeatherForecastByLocation(location: String): ResultWrapper<MutableList<WeatherData>> {
        return when (val result = remoteDataSourceImpl.getWeatherForecastByLocation(location, Constants.DEFAULT_FORECAST_DAY, appId)) {
            is Success -> {
                val data = mutableListOf<WeatherData>()
                if (result.response?.weatherDataList != null) {
                    data.addAll(result.response.weatherDataList)
                }
                ResultWrapper(data)
            }
            is Failure -> {
                ResultWrapper(result.errorMsg)
            }
        }
    }

    companion object {
        private val TAG = WeatherRepositoryImpl::class.java.simpleName

        private var instance: WeatherRepositoryImpl? = null

        fun getInstance(context: Context): WeatherRepositoryImpl {
            if (instance == null) {
                instance = WeatherRepositoryImpl(LocalDataSourceImpl(), RemoteDataSourceImpl(context))
            }
            return instance!!
        }
    }
}