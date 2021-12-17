package com.example.weathermediasoftkozyrev.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.example.weathermediasoftkozyrev.databinding.CityLayoutBinding
import com.example.weathermediasoftkozyrev.db.CityEntity

import com.example.weathermediasoftkozyrev.ui.WeatherListFragmentDirections
import com.google.android.material.snackbar.Snackbar

class CityAdapter(
    private val fragment: Fragment
) : ListAdapter<CityEntity, CityAdapter.CityViewHolder>(DiffCallback) {

    private var _binding: CityLayoutBinding? = null
    private val binding get() = _binding!!


    companion object DiffCallback : DiffUtil.ItemCallback<CityEntity>() {
        override fun areItemsTheSame(oldItem: CityEntity, newItem: CityEntity): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: CityEntity, newItem: CityEntity): Boolean {
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
        val pref =  holder.itemView.context.getSharedPreferences("SHARED_PREFERENCES",Context.MODE_PRIVATE)
        val cityName = currentCity.name
        val cityLat = currentCity.coord.lat.toFloat()
        val cityLon = currentCity.coord.lon.toFloat()
        val cityId = currentCity._id
        holder.itemView.setOnClickListener {
            val action = WeatherListFragmentDirections.actionListFragmentToDetailFragment(
                lat = cityLat,
                lon = cityLon,
                cityName = cityName,
            )
            NavHostFragment.findNavController(fragment).navigate(action)
        }
        binding.btnSendLocation.setOnClickListener {
            Snackbar.make(binding.root, "Город по умолчанию: $cityName", Snackbar.LENGTH_LONG).show()
            pref.edit().apply {
                      putFloat("LAT", cityLat)
                      putFloat("LON",cityLon)
                      putString("CITY",cityName)
                      apply()
            }
        }
    }

    inner class CityViewHolder(itemBinding: CityLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        private val cityName = binding.cityName
        fun bind(city: CityEntity) {
            city.let {
                cityName.text = it.name
            }
        }
    }
}
