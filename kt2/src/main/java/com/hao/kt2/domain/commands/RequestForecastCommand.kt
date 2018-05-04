package com.hao.kt2.domain.commands

import com.hao.kt2.domain.datasource.ForecastProvider
import com.hao.kt2.domain.model.ForecastList

/**
 * @author Yang Shihao
 */
class RequestForecastCommand(private val zipCode: Long,
                             private val forecastProvider: ForecastProvider = ForecastProvider()) :
        Command<ForecastList> {

    companion object {
        const val DAYS = 7
    }

    override fun execute() = forecastProvider.requestByZipCode(zipCode, DAYS)
}