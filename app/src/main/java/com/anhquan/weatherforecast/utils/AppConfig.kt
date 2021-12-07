package com.anhquan.weatherforecast.utils

import android.content.Context
import android.util.Log
import com.anhquan.weatherforecast.BuildConfig
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

class AppConfig private constructor(properties: Properties) {

    companion object {
        private val TAG = AppConfig::class.java.simpleName

        private var appConfig: AppConfig? = null

        fun getInstance(context: Context): AppConfig {
            if (appConfig == null) {
                val properties = getProperties(context)
                appConfig = AppConfig(properties)
            }
            return appConfig!!
        }

        private fun getProperties(context: Context): Properties {
            val properties = Properties()
            val configName: String = when (BuildConfig.BUILD_TYPE) {
                "release" -> "release.properties"
                else -> "debug.properties"
            }
            try {
                val reader = BufferedReader(InputStreamReader(context.assets.open(configName), "UTF-8"))
                properties.load(reader)
                reader.close()
            } catch (exception: Exception) {
                Log.e(TAG, "Exception when load properties=$exception")
            }
            return properties
        }
    }

    val appId = properties.getProperty("APP_ID") ?: ""
}