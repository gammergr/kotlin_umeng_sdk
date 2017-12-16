package com.qt.basep.domain.datasource

import com.qt.basep.data.db.ForecastDb
import com.qt.basep.data.server.ForecastServer
import com.qt.basep.domain.model.Forecast
import com.qt.basep.domain.model.ForecastList
import com.qt.basep.extensions.firstResult

class ForecastProvider(private val sources: List<com.qt.basep.domain.datasource.ForecastDataSource> = com.qt.basep.domain.datasource.ForecastProvider.Companion.SOURCES) {

    companion object {
        val DAY_IN_MILLIS = 1000 * 60 * 60 * 24
        val SOURCES by lazy { listOf(com.qt.basep.data.db.ForecastDb(), com.qt.basep.data.server.ForecastServer()) }
    }

    fun requestByZipCode(zipCode: Long, days: Int): com.qt.basep.domain.model.ForecastList = requestToSources {
        val res = it.requestForecastByZipCode(zipCode, todayTimeSpan())
        if (res != null && res.size >= days) res else null
    }

    fun requestForecast(id: Long): com.qt.basep.domain.model.Forecast = requestToSources { it.requestDayForecast(id) }

    private fun todayTimeSpan() = System.currentTimeMillis() / com.qt.basep.domain.datasource.ForecastProvider.Companion.DAY_IN_MILLIS * com.qt.basep.domain.datasource.ForecastProvider.Companion.DAY_IN_MILLIS

    private fun <T : Any> requestToSources(f: (com.qt.basep.domain.datasource.ForecastDataSource) -> T?): T = sources.firstResult { f(it) }

}