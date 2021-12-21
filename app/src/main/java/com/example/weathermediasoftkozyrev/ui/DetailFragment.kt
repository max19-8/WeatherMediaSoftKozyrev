package com.example.weathermediasoftkozyrev.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.weathermediasoftkozyrev.R
import com.example.weathermediasoftkozyrev.adapter.PagerAdapter
import com.example.weathermediasoftkozyrev.databinding.FragmentDetailBinding
import com.example.weathermediasoftkozyrev.model.Daily
import com.example.weathermediasoftkozyrev.model.Responses
import com.example.weathermediasoftkozyrev.resource.Status
import com.example.weathermediasoftkozyrev.utils.showSnackBar
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

                getList(args.lat, args.lon,args.cityId).observe(viewLifecycleOwner,
                    { responses ->
                            responses.let { resource ->
                                    when (resource.status) {
                                        Status.SUCCESS -> {
                                            if (resource.data?.daily == null){
                                                updateUI()
                                            }else {
                                                hideProgressBar()
                                                saveWeather(resource.data, args.cityId)
                                                adapter.submitList(resource.data.daily)
                                                binding.textViewCityName.text = args.cityName
                                                Log.d("DETAIL", args.cityName)
                                                Log.d("VIEWMODELSAVE", "${resource.data}")
                                            }
                                        }
                                        Status.LOADING -> {
                                            showProgressBar()
                                        }
                                        Status.ERROR -> {
                                            hideProgressBar()
                                            showSnackBar(binding.root,getString(R.string.something_went_wrong))
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

    private fun updateUI() {
        showSnackBar(binding.root,getString(R.string.no_internet_connection))
            binding.viewpager2.visibility = View.GONE
            binding.textViewCityName.visibility = View.GONE
            binding.detailProgressBar.visibility = View.GONE
            binding.mockDetail.visibility = View.VISIBLE

    }
}