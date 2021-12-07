package com.anhquan.weatherforecast.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    fun formatDateTime(timestampInMillis: Long): String {
        val date = Date(timestampInMillis)
        val timeZoneDate = SimpleDateFormat("E, dd MMM yyyy", Locale.getDefault())
        return timeZoneDate.format(date)
    }
}