package com.hao.kt2.domain.datasource

import com.hao.kt2.domain.model.Forecast
import com.hao.kt2.domain.model.ForecastList
import com.hao.kt2.extensions.firstResult
import com.hao.kt2.server.ForecastServer
import com.socks.library.KLog

/**
 * @author Yang Shihao
 */
class ForecastProvider(private val sources: List<ForecastDataSource> = ForecastProvider.SOURCES) {

    private val TAG = "ForecastByZipCodeRequest"

    companion object {
        const val DAY_IN_MILLIS = 1000 * 60 * 60 * 24
        val SOURCES by lazy { listOf( ForecastServer()) }
    }

    fun requestByZipCode(zipCode: Long, days: Int): ForecastList = requestToSources {
        val res = it.requestForecastByZipCode(zipCode, days)
        if (res != null && res.size != 0) res else null
    }

    fun requestForecast(id: Long): Forecast = requestToSources { it.requestDayForecast(id) }

    private fun todayTimeSpan() = System.currentTimeMillis() / DAY_IN_MILLIS * DAY_IN_MILLIS

    private fun <T : Any> requestToSources(f: (ForecastDataSource) -> T?): T {

        return sources.firstResult {
            KLog.d(TAG, it.toString())
            f(it)
        }
    }
}