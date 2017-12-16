package com.qt.basep.domain.commands

import com.qt.basep.domain.datasource.ForecastProvider
import com.qt.basep.domain.model.Forecast

class RequestDayForecastCommand(
        val id: Long,
        private val forecastProvider: com.qt.basep.domain.datasource.ForecastProvider = com.qt.basep.domain.datasource.ForecastProvider()) :
        com.qt.basep.domain.commands.Command<com.qt.basep.domain.model.Forecast> {

    override fun execute() = forecastProvider.requestForecast(id)
}