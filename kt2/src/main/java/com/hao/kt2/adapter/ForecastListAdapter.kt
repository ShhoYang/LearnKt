package com.hao.kt2.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hao.kt2.R
import com.hao.kt2.domain.model.Forecast
import com.hao.kt2.domain.model.ForecastList
import com.hao.kt2.extensions.ctx
import com.hao.kt2.extensions.toDateString
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_forecast.*

/**
 * @author Yang Shihao
 */
class ForecastListAdapter(private val weekForecast: ForecastList,
                          private val itemClick: (Forecast) -> Unit) :
        RecyclerView.Adapter<ForecastListAdapter.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.ctx).inflate(R.layout.item_forecast, parent, false), itemClick)
    }

    override fun getItemCount() = weekForecast.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindForecast(weekForecast[position])
    }

    class VH(override val containerView: View, private val itemClick: (Forecast) -> Unit)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindForecast(forecast: Forecast) {
            with(forecast) {
                Picasso.with(itemView.ctx).load(iconUrl).into(icon)
                dateText.text = date.toDateString()
                descriptionText.text = description
                maxTemperature.text = "${high}°"
                minTemperature.text = "${low}°"
                itemView.setOnClickListener{itemClick(this)}
            }
        }
    }
}