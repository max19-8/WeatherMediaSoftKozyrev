package com.example.weathermediasoftkozyrev.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.weathermediasoftkozyrev.MainActivity
import com.example.weathermediasoftkozyrev.R
import com.example.weathermediasoftkozyrev.databinding.FragmentDetailBinding
import com.example.weathermediasoftkozyrev.repository.CityWeatherRepository
import com.example.weathermediasoftkozyrev.resource.Status
import com.example.weathermediasoftkozyrev.viewmodel.DetailWeatherViewModel
import com.example.weathermediasoftkozyrev.viewmodel.WeatherViewModelFactory

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DetailWeatherViewModel
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
                viewModel = ViewModelProvider(this,
                    WeatherViewModelFactory(CityWeatherRepository())
        )[DetailWeatherViewModel::class.java]
        getCityWeatherDescription()
        return binding.root
    }

    private fun getCityWeatherDescription() {
        viewModel.getCityWeather(args.cityId).observe(viewLifecycleOwner,{
            it?.let { resource ->
                when(resource.status) {
                    Status.SUCCESS -> {
                        hideProgressBar()
                        showItems()
                        binding.textView2.text = resource.data?.body()?.name
                        binding.textView3.text = getString(R.string.feels_like, resource.data?.body()?.main?.feels_like.toString())
                        binding.textView4.text = getString(R.string.temp_min, resource.data?.body()?.main?.temp_min.toString())
                        binding.textView5.text = getString(R.string.temp_max,  resource.data?.body()?.main?.temp_max.toString())
                        binding.textView6.text = getString(R.string.wind_speed, resource.data?.body()?.wind?.speed.toString())
                        binding.textView7.text = resource.data?.body()!!.weather[0].description
                    }
                    Status.ERROR -> {
                        hideProgressBar()
                        hideItems()
                    }
                    Status.LOADING -> {
                        showProgressBar()
                        hideItems()
                    }
                }
            }
        })
    }
    private fun showItems(){
        binding.textView2.visibility = View.VISIBLE
        binding.textView3.visibility = View.VISIBLE
        binding.textView4.visibility = View.VISIBLE
        binding.textView5.visibility = View.VISIBLE
        binding.textView6.visibility = View.VISIBLE
        binding.textView7.visibility = View.VISIBLE
    }
    private fun hideItems(){
        binding.textView2.visibility = View.INVISIBLE
        binding.textView3.visibility = View.INVISIBLE
        binding.textView4.visibility = View.INVISIBLE
        binding.textView5.visibility = View.INVISIBLE
        binding.textView6.visibility = View.INVISIBLE
        binding.textView7.visibility = View.INVISIBLE
    }
    private fun showProgressBar() {
        (requireActivity() as MainActivity).showProgressBar()
    }
    private fun hideProgressBar() {
        (requireActivity() as MainActivity).hideProgressBar()
    }


}