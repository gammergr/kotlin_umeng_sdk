package com.qt.basep.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.qt.basep.ui.App
import org.jetbrains.anko.db.*

class ForecastDbHelper(ctx: Context = App.instance) : ManagedSQLiteOpenHelper(ctx,
        com.qt.basep.data.db.ForecastDbHelper.Companion.DB_NAME, null, com.qt.basep.data.db.ForecastDbHelper.Companion.DB_VERSION) {

    companion object {
        val DB_NAME = "forecast.db"
        val DB_VERSION = 1
        val instance by lazy { com.qt.basep.data.db.ForecastDbHelper() }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(com.qt.basep.data.db.CityForecastTable.NAME, true,
                com.qt.basep.data.db.CityForecastTable.ID to INTEGER + PRIMARY_KEY,
                com.qt.basep.data.db.CityForecastTable.CITY to TEXT,
                com.qt.basep.data.db.CityForecastTable.COUNTRY to TEXT)

        db.createTable(com.qt.basep.data.db.DayForecastTable.NAME, true,
                com.qt.basep.data.db.DayForecastTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                com.qt.basep.data.db.DayForecastTable.DATE to INTEGER,
                com.qt.basep.data.db.DayForecastTable.DESCRIPTION to TEXT,
                com.qt.basep.data.db.DayForecastTable.HIGH to INTEGER,
                com.qt.basep.data.db.DayForecastTable.LOW to INTEGER,
                com.qt.basep.data.db.DayForecastTable.ICON_URL to TEXT,
                com.qt.basep.data.db.DayForecastTable.CITY_ID to INTEGER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(com.qt.basep.data.db.CityForecastTable.NAME, true)
        db.dropTable(com.qt.basep.data.db.DayForecastTable.NAME, true)
        onCreate(db)
    }
}

