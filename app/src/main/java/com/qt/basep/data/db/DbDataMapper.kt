package com.qt.basep.data.db

import com.qt.basep.domain.model.Forecast
import com.qt.basep.domain.model.ForecastList

class DbDataMapper {

    fun convertFromDomain(forecast: com.qt.basep.domain.model.ForecastList) = with(forecast) {
        val daily = dailyForecast.map { convertDayFromDomain(id, it) }
        com.qt.basep.data.db.CityForecast(id, city, country, daily)
    }

    private fun convertDayFromDomain(cityId: Long, forecast: com.qt.basep.domain.model.Forecast) = with(forecast) {
        com.qt.basep.data.db.DayForecast(date, description, high, low, iconUrl, cityId)
    }

    fun convertToDomain(forecast: com.qt.basep.data.db.CityForecast) = with(forecast) {
        val daily = dailyForecast.map { convertDayToDomain(it) }
        com.qt.basep.domain.model.ForecastList(_id, city, country, daily)
    }

    fun convertDayToDomain(dayForecast: com.qt.basep.data.db.DayForecast) = with(dayForecast) {
        com.qt.basep.domain.model.Forecast(_id, date, description, high, low, iconUrl)
    }
}