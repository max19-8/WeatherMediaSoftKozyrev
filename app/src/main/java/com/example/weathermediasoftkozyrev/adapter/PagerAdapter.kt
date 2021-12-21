package com.example.weathermediasoftkozyrev.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weathermediasoftkozyrev.R
import com.example.weathermediasoftkozyrev.databinding.DetailViewBinding
import com.example.weathermediasoftkozyrev.model.Daily
import java.text.SimpleDateFormat
import java.util.*

class PagerAdapter(private val context: Context) :
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
        private val tvDate = binding.textViewDate
        private val tvDesc = binding.textViewDescription
        private val weatherIcon = binding.weatherIcon
        private val tvMaxTemp = binding.textViewMaxTemp
        private val tvMinTemp = binding.textViewMinTemp
        private val tvMorningTemp = binding.textViewTempMorningValue
        private val tvDayTemp = binding.textViewTempDayValue
        private val tvEveningTemp = binding.textViewTempEveningValue
        private val tvNightTemp = binding.textViewTempNightValue
        private val tvSunrise = binding.textViewSunriseValue
        private val tvSunset = binding.textViewSunsetValue
        private val tvPressure = binding.textViewPressureValue
        private val tvWindSpeed = binding.textViewWindSpeedValue
        private val tvClouds = binding.textViewCloudsValue
        private val tvDewPoint = binding.textViewDewPointValue
        private val customView = binding.customView

        fun bind(day: Daily) {
            tvDate.text = SimpleDateFormat("dd.MM.yyyy", Locale.ROOT).format(Date(day.dt * 1000L))
            tvDesc.text = day.weather[0].description
            Glide.with(context)
                .load("https://openweathermap.org/img/wn/" + day.weather[0].icon + "@2x.png")
                .into(weatherIcon)
            tvMaxTemp.text = itemView.context.getString(R.string.temp_max, day.temp.max.toString())
            tvMinTemp.text = itemView.context.getString(R.string.temp_min, day.temp.min.toString())
            customView.setProgress(day.humidity)
            tvMorningTemp.text = itemView.context.getString(R.string.temp, day.temp.morn.toString())
            tvDayTemp.text = itemView.context.getString(R.string.temp, day.temp.day.toString())
            tvEveningTemp.text = itemView.context.getString(R.string.temp, day.temp.eve.toString())
            tvNightTemp.text = itemView.context.getString(R.string.temp, day.temp.night.toString())
            tvSunrise.text =
                SimpleDateFormat("HH:mm:ss", Locale.ROOT).format(Date(day.sunrise * 1000L))
            tvSunset.text =
                SimpleDateFormat("HH:mm:ss", Locale.ROOT).format(Date(day.sunset * 1000L))
            tvPressure.text =
                itemView.context.getString(R.string.pressure_value, day.pressure.toString())
            tvWindSpeed.text =
                itemView.context.getString(R.string.wind_speed_value, day.wind_speed.toString())
            tvClouds.text = itemView.context.getString(R.string.clouds_value, day.clouds.toString())
            tvDewPoint.text = itemView.context.getString(R.string.temp, day.dew_point.toString())

            binding.buttonSendWeather.setOnClickListener {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        SimpleDateFormat("dd.MM.yyyy", Locale.ROOT).format(Date(day.dt * 1000L)) +
                                "\n" + context.getString(
                            R.string.weather_day,
                            day.temp.day.toString()
                        ) +
                                "\n" + context.getString(
                            R.string.temp_max,
                            day.temp.max.toString()
                        ) +
                                "\n" + context.getString(R.string.temp_min, day.temp.min.toString())
                    )
                    type = "text/plain"
                }
                itemView.context.startActivity(intent)
            }

        }
    }
}