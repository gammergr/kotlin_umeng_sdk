package com.qt.basep.domain.datasource

import com.qt.basep.domain.model.Forecast
import com.qt.basep.domain.model.ForecastList

interface ForecastDataSource {

    fun requestForecastByZipCode(zipCode: Long, date: Long): com.qt.basep.domain.model.ForecastList?

    fun requestDayForecast(id: Long): com.qt.basep.domain.model.Forecast?

}