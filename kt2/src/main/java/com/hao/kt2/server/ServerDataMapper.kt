package com.hao.kt2.server

import com.hao.kt2.domain.model.ForecastList
import java.util.*
import java.util.concurrent.TimeUnit
import com.hao.kt2.domain.model.Forecast as ModelForecast

/**
 * @author Yang Shihao
 */
class ServerDataMapper {

    fun convertToDomain(zipCode: Long, forecast: ForecastResult) = with(forecast) {
        ForecastList(zipCode, city.name, city.country, convertFroecastListToDomain(list))
    }

    private fun convertFroecastListToDomain(list: List<Forecast>): List<ModelForecast> {
        return list.mapIndexed { index, forecast ->
            val dt = Calendar.getInstance().timeInMillis + TimeUnit.DAYS.toMillis(index.toLong())
            convertForecastItemToDomain(forecast.copy(dt = dt))
        }
    }

    private fun convertForecastItemToDomain(forecast: Forecast) = with(forecast) {
        ModelForecast(-1, dt, weather[0].description, temp.max.toInt(), temp.min.toInt(), generateIconUrl(weather[0].icon))
    }

    private fun generateIconUrl(iconCode: String) = "http://openweathermap/org/img/w$iconCode.png"
}