package com.anhquan.weatherforecast.utils

import android.util.Base64
import android.util.Log
import com.anhquan.weatherforecast.MainApplication
import java.lang.Exception
import java.nio.charset.StandardCharsets

/**
 * Not a best solution to encode/decode the AppID using Base64. But in this assignment's scope, it's rather than
 * showing AppID naked in source code without encryption.
 */
object KeyUtils {

    fun getAppId(): String {
        val encodedAppId = AppConfig.getInstance(MainApplication.instance).appId
        return try {
            val decodedAppId = Base64.decode(encodedAppId.toByteArray(), Base64.DEFAULT)
            String(decodedAppId, StandardCharsets.UTF_8)
        } catch (e: Exception) {
            Log.d("KeyUtils", "e=$e")
            ""
        }
    }
}