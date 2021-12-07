package com.anhquan.weatherforecast.data

data class ResultWrapper<T> private constructor(val value: T?, val errorMsg: String?) {
    constructor(value: T) : this(value, null)
    constructor(errorMsg: String) : this(null, errorMsg)
}