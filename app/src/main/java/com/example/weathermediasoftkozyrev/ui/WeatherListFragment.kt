package com.example.weathermediasoftkozyrev.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathermediasoftkozyrev.R
import com.example.weathermediasoftkozyrev.adapter.CityAdapter
import com.example.weathermediasoftkozyrev.databinding.FragmentListBinding
import com.example.weathermediasoftkozyrev.mock.NewCity
import com.example.weathermediasoftkozyrev.mock.NewCityItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class WeatherListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var cityAdapter: CityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(layoutInflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        val jsonFileString = getJsonDataFromAsset(requireContext(), "city_list.json")
        val gson = Gson()
        val listPersonType = object : TypeToken<NewCity>() {}.type
        val cities: List<NewCityItem> = gson.fromJson(jsonFileString, listPersonType)
        cities.forEachIndexed { _, city -> Log.i("data", "> Item ${city.country}") }
        cityAdapter = CityAdapter(this)
        binding.rvCities.apply {
            adapter = cityAdapter
            layoutManager = LinearLayoutManager(activity)
            cityAdapter.submitList(cities)
        }
    }
    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
            val jsonString: String
            try {
                jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                return null
            }
            return jsonString
        }
}

