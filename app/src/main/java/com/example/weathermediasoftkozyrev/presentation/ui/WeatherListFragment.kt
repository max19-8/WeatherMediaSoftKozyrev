package com.example.weathermediasoftkozyrev.presentation.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathermediasoftkozyrev.R
import com.example.weathermediasoftkozyrev.presentation.adapter.cityadapter.CityAdapter
import com.example.weathermediasoftkozyrev.presentation.adapter.cityadapter.CityClickListener
import com.example.weathermediasoftkozyrev.presentation.adapter.cityadapter.SendLocationListener
import com.example.weathermediasoftkozyrev.databinding.FragmentListBinding
import com.example.weathermediasoftkozyrev.domain.utils.itemTouchHelper
import com.example.weathermediasoftkozyrev.presentation.viewmodel.ListCityViewModel
import com.google.android.material.snackbar.Snackbar

class WeatherListFragment : BaseFragment<FragmentListBinding>(), CityClickListener,
    SendLocationListener {

    override fun getViewBinding(): FragmentListBinding =
        FragmentListBinding.inflate(layoutInflater)

    private var cityAdapter: CityAdapter? = null
    private val viewModel: ListCityViewModel by viewModels()
    private var pref: SharedPreferences? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)
        pref = requireContext().getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.recyclerViewCities

        cityAdapter = CityAdapter(this, this)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = cityAdapter
        }
        viewModel.getListCities().observe(viewLifecycleOwner, { cities ->
            cityAdapter?.submitList(cities)
        })
        val itemTouchHelperCallback = ItemTouchHelper(itemTouchHelper)
        itemTouchHelperCallback.attachToRecyclerView(recyclerView)
    }

    override fun onCityClick(cityLat: Float, cityLon: Float, cityId: Int, cityName: String) {
        val action = WeatherListFragmentDirections.actionListFragmentToDetailFragment(
            lat = cityLat,
            lon = cityLon,
            cityId = cityId,
            cityName = cityName
        )
        view?.findNavController()?.navigate(action)
    }

    override fun sendLocation(cityLat: Float, cityLon: Float, cityName: String) {
        Snackbar.make(binding.root, "Город по умолчанию: $cityName", Snackbar.LENGTH_LONG)
            .show()
        pref!!.edit().apply {
            putFloat("LAT", cityLat)
            putFloat("LON", cityLon)
            putString("CITY", cityName)
            apply()
        }
    }
}