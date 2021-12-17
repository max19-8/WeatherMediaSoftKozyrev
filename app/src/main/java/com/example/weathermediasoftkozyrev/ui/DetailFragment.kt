package com.example.weathermediasoftkozyrev.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.weathermediasoftkozyrev.adapter.PagerAdapter
import com.example.weathermediasoftkozyrev.databinding.FragmentDetailBinding
import com.example.weathermediasoftkozyrev.model.Responses
import com.example.weathermediasoftkozyrev.resource.Status
import com.example.weathermediasoftkozyrev.viewmodel.DetailWeatherViewModel
import com.google.android.material.snackbar.Snackbar

class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    override fun getViewBinding(): FragmentDetailBinding =
        FragmentDetailBinding.inflate(layoutInflater)

    private val viewModel: DetailWeatherViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: PagerAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewPager()
        getDetailWeather()
    }
    private fun setUpViewPager() {
        adapter = PagerAdapter(requireContext())
        viewPager2 = binding.viewpager2
        viewPager2.adapter = adapter
    }
    private fun getDetailWeather() {
        viewModel.apply {
                getList(args.lat, args.lon).observe(viewLifecycleOwner, { weather1 ->
                    weather1.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                hideProgressBar()
                                saveWeather(resource.data)
                                adapter.submitList(resource.data?.daily)
                                binding.textViewCityName.text = args.cityName
                                Log.d("DETAIL", "${resource.data}")

                                Log.d("VIEWMODELSAVE","${resource.data}")
                            }
                            Status.LOADING -> {
                                showProgressBar()
                            }
                            Status.ERROR -> {
                                hideProgressBar()
                                Snackbar.make(binding.root, Status.ERROR.name, Snackbar.LENGTH_LONG).show()
                            }
                        }
                    }
                }
                )
        }
    }
    private fun showProgressBar() {
        binding.detailProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.detailProgressBar.visibility = View.INVISIBLE
    }
}