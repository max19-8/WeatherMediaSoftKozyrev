package com.example.weathermediasoftkozyrev.presentation.adapter.pageradapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weathermediasoftkozyrev.R
import com.example.weathermediasoftkozyrev.data.model.Daily
import com.example.weathermediasoftkozyrev.databinding.DetailViewBinding
import com.example.weathermediasoftkozyrev.domain.utils.Constants.FORMAT_DATE
import com.example.weathermediasoftkozyrev.domain.utils.Constants.FORMAT_TIME
import java.text.SimpleDateFormat
import java.util.*

class PagerAdapter(private val sendWeatherInfoListener: SendWeatherInfoListener) :
    ListAdapter<Daily, PagerAdapter.ViewPagerHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Daily>() {
        override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean {
            return oldItem.dt == newItem.dt
        }

        override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewPagerHolder(
            binding = DetailViewBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        val currentDay = currentList[position]
        holder.bind(currentDay)
    }

    inner class ViewPagerHolder(private val binding: DetailViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(day: Daily) {
            with(binding) {
                textViewDate.text =
                    SimpleDateFormat(FORMAT_DATE, Locale.ROOT).format(Date(day.dt * 1000L))
                textViewDescription.text = day.weather[0].description
                Glide.with(itemView.context)
                    .load("https://openweathermap.org/img/wn/" + day.weather[0].icon + "@2x.png")
                    .into(weatherIcon)
                textViewMaxTemp.text =
                    itemView.context.getString(R.string.temp_max, day.temp.max.toString())
                textViewMinTemp.text =
                    itemView.context.getString(R.string.temp_min, day.temp.min.toString())
                customView.setProgress(day.humidity)
                textViewTempMorningValue.text =
                    itemView.context.getString(R.string.temp, day.temp.morn.toString())
                textViewTempDayValue.text =
                    itemView.context.getString(R.string.temp, day.temp.day.toString())
                textViewTempEveningValue.text =
                    itemView.context.getString(R.string.temp, day.temp.eve.toString())
                textViewTempNightValue.text =
                    itemView.context.getString(R.string.temp, day.temp.night.toString())
                textViewSunriseValue.text =
                    SimpleDateFormat(FORMAT_TIME, Locale.ROOT).format(Date(day.sunrise * 1000L))
                textViewSunsetValue.text =
                    SimpleDateFormat(FORMAT_TIME, Locale.ROOT).format(Date(day.sunset * 1000L))
                textViewPressureValue.text =
                    itemView.context.getString(R.string.pressure_value, day.pressure.toString())
                textViewWindSpeedValue.text =
                    itemView.context.getString(R.string.wind_speed_value, day.wind_speed.toString())
                textViewCloudsValue.text =
                    itemView.context.getString(R.string.clouds_value, day.clouds.toString())
                textViewDewPointValue.text =
                    itemView.context.getString(R.string.temp, day.dew_point.toString())

                buttonSendWeather.setOnClickListener {
                    sendWeatherInfoListener.sendWeatherInfo(
                        day.dt,
                        day.temp.day,
                        day.temp.max,
                        day.temp.min
                    )
                }
            }
        }
    }
}