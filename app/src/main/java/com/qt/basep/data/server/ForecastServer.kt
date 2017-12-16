package com.qt.basep.data.server

import com.qt.basep.data.db.ForecastDb
import com.qt.basep.domain.datasource.ForecastDataSource
import com.qt.basep.domain.model.ForecastList

class ForecastServer(private val dataMapper: com.qt.basep.data.server.ServerDataMapper = com.qt.basep.data.server.ServerDataMapper(),
                     private val forecastDb: com.qt.basep.data.db.ForecastDb = com.qt.basep.data.db.ForecastDb()) : com.qt.basep.domain.datasource.ForecastDataSource {

    override fun requestForecastByZipCode(zipCode: Long, date: Long): com.qt.basep.domain.model.ForecastList? {
        val result = com.qt.basep.data.server.ForecastByZipCodeRequest(zipCode).execute()
        val converted = dataMapper.convertToDomain(zipCode, result)
        forecastDb.saveForecast(converted)
        return forecastDb.requestForecastByZipCode(zipCode, date)
    }

    override fun requestDayForecast(id: Long) = throw UnsupportedOperationException()
}