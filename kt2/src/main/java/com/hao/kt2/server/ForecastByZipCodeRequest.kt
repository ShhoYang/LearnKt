package com.hao.kt2.server

import com.google.gson.Gson
import java.net.URL

/**
 * @author Yang Shihao
 */
class ForecastByZipCodeRequest(private val zipCode: Long, val gson: Gson = Gson()) {
    companion object {
        private val APP_ID = "15646a06818f61f7b8d7823ca833e1ce"
        private val URL = "http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&units=metric&cnt=7"
        private val COMPLETE_URL = "$URL&APPID=$APP_ID&zip="
    }

    fun execute(): ForecastResult {

        val url = COMPLETE_URL + zipCode
        val forecastJson = URL(url).readText()
        return gson.fromJson(forecastJson, ForecastResult::class.java)
    }
}

