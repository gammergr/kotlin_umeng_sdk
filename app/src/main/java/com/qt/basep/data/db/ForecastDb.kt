package com.qt.basep.data.db

import com.qt.basep.domain.datasource.ForecastDataSource
import com.qt.basep.domain.model.ForecastList
import com.qt.basep.extensions.*
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import java.util.*

class ForecastDb(private val forecastDbHelper: com.qt.basep.data.db.ForecastDbHelper = com.qt.basep.data.db.ForecastDbHelper.Companion.instance,
                 private val dataMapper: com.qt.basep.data.db.DbDataMapper = com.qt.basep.data.db.DbDataMapper()) : com.qt.basep.domain.datasource.ForecastDataSource {

    override fun requestForecastByZipCode(zipCode: Long, date: Long) = forecastDbHelper.use {

        val dailyRequest = "${com.qt.basep.data.db.DayForecastTable.CITY_ID} = ? AND ${com.qt.basep.data.db.DayForecastTable.DATE} >= ?"
        val dailyForecast = select(com.qt.basep.data.db.DayForecastTable.NAME)
                .whereSimple(dailyRequest, zipCode.toString(), date.toString())
                .parseList { com.qt.basep.data.db.DayForecast(HashMap(it)) }

        val city = select(com.qt.basep.data.db.CityForecastTable.NAME)
                .whereSimple("${com.qt.basep.data.db.CityForecastTable.ID} = ?", zipCode.toString())
                .parseOpt { com.qt.basep.data.db.CityForecast(HashMap(it), dailyForecast) }

        city?.let { dataMapper.convertToDomain(it) }
    }

    override fun requestDayForecast(id: Long) = forecastDbHelper.use {
        val forecast = select(com.qt.basep.data.db.DayForecastTable.NAME).byId(id).
                parseOpt { com.qt.basep.data.db.DayForecast(HashMap(it)) }

        forecast?.let { dataMapper.convertDayToDomain(it) }
    }

    fun saveForecast(forecast: com.qt.basep.domain.model.ForecastList) = forecastDbHelper.use {

        clear(com.qt.basep.data.db.CityForecastTable.NAME)
        clear(com.qt.basep.data.db.DayForecastTable.NAME)

        with(dataMapper.convertFromDomain(forecast)) {
            insert(com.qt.basep.data.db.CityForecastTable.NAME, *map.toVarargArray())
            dailyForecast.forEach { insert(com.qt.basep.data.db.DayForecastTable.NAME, *it.map.toVarargArray()) }
        }
    }
}
