package com.hao.kt2.domain.commands

import com.hao.kt2.domain.datasource.ForecastProvider
import com.hao.kt2.domain.model.Forecast

/**
 * @author Yang Shihao
 */
class RequestDayForecastCommand(
        val id: Long,
        private val forecastProvider: ForecastProvider = ForecastProvider()) :
        Command<Forecast> {

    override fun execute() = forecastProvider.requestForecast(id)

}