package com.example.weathermediasoftkozyrev.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathermediasoftkozyrev.R
import com.example.weathermediasoftkozyrev.adapter.CityAdapter
import com.example.weathermediasoftkozyrev.databinding.FragmentListBinding
import com.example.weathermediasoftkozyrev.utils.itemTouchHelper
import com.example.weathermediasoftkozyrev.viewmodel.ListCityViewModel

class WeatherListFragment : BaseFragment<FragmentListBinding>(){

    override fun getViewBinding(): FragmentListBinding =
        FragmentListBinding.inflate(layoutInflater)

    private lateinit var cityAdapter: CityAdapter

    private val viewModel: ListCityViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)
        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        val recyclerVIew = binding.recyclerViewCities
        cityAdapter = CityAdapter(this)
        recyclerVIew.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = cityAdapter
        }
        viewModel.getListCities().observe(viewLifecycleOwner, {cities ->
            cityAdapter.submitList(cities)
        })
        val itemTouchHelperCallback = ItemTouchHelper(itemTouchHelper)
        itemTouchHelperCallback.attachToRecyclerView(recyclerVIew)
    }
}

