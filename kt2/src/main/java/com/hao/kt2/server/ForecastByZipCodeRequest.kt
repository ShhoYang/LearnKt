package com.hao.kt2.server

import com.google.gson.Gson
import com.socks.library.KLog
import java.net.URL

/**
 * @author Yang Shihao
 */
class ForecastByZipCodeRequest(private val zipCode: Long, private val date: Int = 7, val gson: Gson = Gson()) {

    private val TAG = "ForecastByZipCodeRequest"

    companion object {
        private val APP_ID = "15646a06818f61f7b8d7823ca833e1ce"
        private val URL = "http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&units=metric"
    }

    fun execute(): ForecastResult {

        val url = "$URL&APPID=$APP_ID&zip=$zipCode&cnt=$date"
        KLog.d(TAG, "url: $url")
        val json = URL(url).readText()
        KLog.json(TAG, json)
        return gson.fromJson(json, ForecastResult::class.java)
    }
}

