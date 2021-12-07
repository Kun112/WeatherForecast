package com.anhquan.weatherforecast.network

import android.content.Context
import com.anhquan.weatherforecast.utils.Constants
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object ServiceGenerator {

    fun provideWeatherApiService(context: Context): WeatherApiService {
        val httpClient = OkHttpClient.Builder().cache(createCache(context))
            .addNetworkInterceptor(createNetworkInterceptor())
            .addInterceptor(createLoggingInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

        return retrofit.create(WeatherApiService::class.java)
    }

    private fun createCache(context: Context): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MB cache
        val cacheDir = File(context.cacheDir, "http-cache")
        return Cache(cacheDir, cacheSize.toLong())
    }

    private fun createNetworkInterceptor(): Interceptor {
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())

            val cacheControl = CacheControl.Builder()
                .maxAge(5, TimeUnit.MINUTES)    // 5 min for cached data
                .build()

            response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .build()
        }
    }

    private fun createLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }
}