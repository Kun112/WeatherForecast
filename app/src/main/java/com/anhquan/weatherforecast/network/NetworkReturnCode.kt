package com.anhquan.weatherforecast.network

object NetworkReturnCode {
    const val UNKNOWN_ERROR = 600
    const val NO_INTERNET_CONNECTION = 601
    const val REJECTED_REQUEST = 666
    const val RESPONSE_OK = 200
    const val BAD_REQUEST = 400
    const val UNAUTHORIZED = 401
    const val NOT_FOUND = 404
    const val CONTENT_TYPE_ERROR = 415
    const val INTERNAL_SERVER_ERROR = 500
    const val SERVER_NOT_RESPONDING = 504
    const val CLIENT_TIMEOUT = 408
}