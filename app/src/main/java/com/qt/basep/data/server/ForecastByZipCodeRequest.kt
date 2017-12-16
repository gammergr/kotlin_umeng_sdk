package com.qt.basep.data.server

import com.google.gson.Gson
import java.net.URL

class ForecastByZipCodeRequest(private val zipCode: Long, val gson: Gson = Gson()) {

    companion object {
        private val APP_ID = "15646a06818f61f7b8d7823ca833e1ce"
        private val URL = "http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&units=metric&cnt=7"
        private val COMPLETE_URL = "${com.qt.basep.data.server.ForecastByZipCodeRequest.Companion.URL}&APPID=${com.qt.basep.data.server.ForecastByZipCodeRequest.Companion.APP_ID}&zip="
    }

    fun execute(): com.qt.basep.data.server.ForecastResult {
        val forecastJsonStr = URL(com.qt.basep.data.server.ForecastByZipCodeRequest.Companion.COMPLETE_URL + zipCode).readText()
        return gson.fromJson(forecastJsonStr, com.qt.basep.data.server.ForecastResult::class.java)
    }
}