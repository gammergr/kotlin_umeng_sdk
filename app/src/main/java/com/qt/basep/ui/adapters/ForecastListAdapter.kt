package com.qt.basep.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qt.basep.R
import com.qt.basep.domain.model.Forecast
import com.qt.basep.domain.model.ForecastList
import com.qt.basep.extensions.ctx
import com.qt.basep.extensions.toDateString
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_forecast.view.*

class ForecastListAdapter(private val weekForecast: com.qt.basep.domain.model.ForecastList,
                          private val itemClick: (com.qt.basep.domain.model.Forecast) -> Unit) :
        RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindForecast(weekForecast[position])
    }

    override fun getItemCount() = weekForecast.size

    class ViewHolder(view: View, private val itemClick: (com.qt.basep.domain.model.Forecast) -> Unit)
        : RecyclerView.ViewHolder(view) {

        fun bindForecast(forecast: com.qt.basep.domain.model.Forecast) {
            with(forecast) {
                Picasso.with(itemView.ctx).load(iconUrl).into(itemView.icon)
                itemView.date.text = date.toDateString()
                itemView.description.text = description
                itemView.maxTemperature.text = "${high}º"
                itemView.minTemperature.text = "${low}º"
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}