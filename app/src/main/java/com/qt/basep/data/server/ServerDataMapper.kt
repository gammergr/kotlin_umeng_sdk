package com.qt.basep.data.server

import com.qt.basep.domain.model.ForecastList
import java.util.*
import java.util.concurrent.TimeUnit
import com.qt.basep.domain.model.Forecast as ModelForecast

class ServerDataMapper {

    fun convertToDomain(zipCode: Long, forecast: com.qt.basep.data.server.ForecastResult) = with(forecast) {
        com.qt.basep.domain.model.ForecastList(zipCode, city.name, city.country, convertForecastListToDomain(list))
    }

    private fun convertForecastListToDomain(list: List<com.qt.basep.data.server.Forecast>): List<ModelForecast> {
        return list.mapIndexed { i, forecast ->
            val dt = Calendar.getInstance().timeInMillis + TimeUnit.DAYS.toMillis(i.toLong())
            convertForecastItemToDomain(forecast.copy(dt = dt))
        }
    }

    private fun convertForecastItemToDomain(forecast: com.qt.basep.data.server.Forecast) = with(forecast) {
        ModelForecast(-1, dt, weather[0].description, temp.max.toInt(), temp.min.toInt(),
                generateIconUrl(weather[0].icon))
    }

    private fun generateIconUrl(iconCode: String) = "http://openweathermap.org/img/w/$iconCode.png"
}