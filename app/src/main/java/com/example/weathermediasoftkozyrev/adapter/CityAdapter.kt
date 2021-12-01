package com.example.weathermediasoftkozyrev.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weathermediasoftkozyrev.R
import com.example.weathermediasoftkozyrev.databinding.CityLayoutBinding
import com.example.weathermediasoftkozyrev.mock.City
import com.example.weathermediasoftkozyrev.mock.NewCityItem
import com.example.weathermediasoftkozyrev.ui.WeatherListFragmentDirections

class CityAdapter(private val fragment: Fragment
): ListAdapter<NewCityItem, CityAdapter.CityViewHolder>(DiffCallback) {

    private var _binding: CityLayoutBinding? = null
    private val binding get() = _binding!!



    companion object DiffCallback : DiffUtil.ItemCallback<NewCityItem>() {
        override fun areItemsTheSame(oldItem: NewCityItem, newItem: NewCityItem): Boolean {
            return oldItem.name == newItem.name
        }
        override fun areContentsTheSame(oldItem: NewCityItem, newItem: NewCityItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        _binding = CityLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
                val currentCity = currentList[position]
                holder.bind(currentCity)

        holder.itemView.setOnClickListener {
            val id = currentCity._id
            val action = WeatherListFragmentDirections.actionListFragmentToDetailFragment(
                cityId = id
            )
            NavHostFragment.findNavController(fragment).navigate(action)
        }
    }

    inner class CityViewHolder(itemBinding:CityLayoutBinding): RecyclerView.ViewHolder(itemBinding.root) {
        private val txtCityName = itemView.findViewById<TextView>(R.id.cityName)
        fun bind(city: NewCityItem) {
            with(city) {
                txtCityName.text = name
            }
        }
    }
}
