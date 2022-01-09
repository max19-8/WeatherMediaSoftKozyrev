package com.example.weathermediasoftkozyrev.presentation.adapter.cityadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weathermediasoftkozyrev.data.model.City
import com.example.weathermediasoftkozyrev.databinding.CityLayoutBinding

class CityAdapter(
    private val cityClickListener: CityClickListener,
    private val sendLocationListener: SendLocationListener
) : ListAdapter<City, CityAdapter.CityViewHolder>(DiffCallback) {

    private var _binding: CityLayoutBinding? = null
    private val binding get() = _binding!!

    companion object DiffCallback : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        _binding = CityLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val currentCity = currentList[position]
        holder.bind(currentCity)
        val cityLat = currentCity.coord.lat.toFloat()
        val cityLon = currentCity.coord.lon.toFloat()
        val cityId = currentCity._id
        val cityName = currentCity.name

        holder.itemView.setOnClickListener {
            cityClickListener.onCityClick(cityLat, cityLon, cityId, cityName)
        }
        binding.btnSendLocation.setOnClickListener {
            sendLocationListener.sendLocation(cityLat, cityLon, cityName)
        }
    }

    inner class CityViewHolder(itemBinding: CityLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(city: City) {
            city.let {
                binding.cityName.text = it.name
            }
        }
    }
}