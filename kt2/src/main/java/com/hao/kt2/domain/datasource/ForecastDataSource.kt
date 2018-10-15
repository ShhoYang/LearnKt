package com.hao.kt2.domain.datasource

import com.hao.kt2.domain.model.Forecast
import com.hao.kt2.domain.model.ForecastList

/**
 * @author Yang Shihao
 */
interface ForecastDataSource {

    fun requestForecastByZipCode(zipCode: Long, days: Int): ForecastList?

    fun requestDayForecast(id: Long): Forecast?
}