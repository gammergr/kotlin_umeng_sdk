package com.qt.basep.domain.commands

import com.qt.basep.domain.datasource.ForecastProvider
import com.qt.basep.domain.model.ForecastList

class RequestForecastCommand(
        private val zipCode: Long,
        private val forecastProvider: com.qt.basep.domain.datasource.ForecastProvider = com.qt.basep.domain.datasource.ForecastProvider()) :
        com.qt.basep.domain.commands.Command<com.qt.basep.domain.model.ForecastList> {

    companion object {
        val DAYS = 7
    }

    override fun execute() = forecastProvider.requestByZipCode(zipCode, com.qt.basep.domain.commands.RequestForecastCommand.Companion.DAYS)
}