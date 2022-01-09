package com.example.weathermediasoftkozyrev.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.weathermediasoftkozyrev.R
import com.example.weathermediasoftkozyrev.presentation.adapter.pageradapter.PagerAdapter
import com.example.weathermediasoftkozyrev.presentation.adapter.pageradapter.SendWeatherInfoListener
import com.example.weathermediasoftkozyrev.databinding.FragmentDetailBinding
import com.example.weathermediasoftkozyrev.domain.utils.Constants.FORMAT_DATE
import com.example.weathermediasoftkozyrev.domain.utils.Constants.TYPE_INTENT
import com.example.weathermediasoftkozyrev.domain.utils.showSnackBar
import com.example.weathermediasoftkozyrev.presentation.viewmodel.DetailWeatherViewModel
import java.text.SimpleDateFormat
import java.util.*

class DetailFragment : BaseFragment<FragmentDetailBinding>(), SendWeatherInfoListener {
    override fun getViewBinding(): FragmentDetailBinding =
        FragmentDetailBinding.inflate(layoutInflater)

    private val viewModel: DetailWeatherViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private var viewPager2: ViewPager2? = null
    private var adapter: PagerAdapter? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewPager()
        viewModel.getDetailWeather(args.lat, args.lon, args.cityId)
        observeData()
    }

    private fun setUpViewPager() {
        adapter = PagerAdapter(this)
        viewPager2 = binding.viewpager2
        viewPager2?.adapter = adapter
    }

    private fun observeData() {
        viewModel.getLiveDataUpdateUI.observe(viewLifecycleOwner, { data ->
            if (data)
                updateUI()
        })
        viewModel.getVisibilityProgress.observe(viewLifecycleOwner, { visibilityProgress ->
            if (visibilityProgress) {
                showProgressBar()
            } else {
                hideProgressBar()
            }
        })
        viewModel.getWeatherResponse.observe(viewLifecycleOwner, { weather ->
            viewModel.saveWeather(weather.data, args.cityId)
            adapter?.submitList(weather.data?.daily)
            binding.textViewCityName.text = args.cityName
        })
    }

    private fun showProgressBar() {
        binding.detailProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.detailProgressBar.visibility = View.INVISIBLE
    }

    private fun updateUI() {
        showSnackBar(binding.root, getString(R.string.no_internet_connection))
        with(binding) {
            viewpager2.isVisible = false
            textViewCityName.isVisible = false
            detailProgressBar.isVisible = false
            mockDetail.isVisible = true
        }
    }

    override fun sendWeatherInfo(date: Int, tempDay: Double, tempMax: Double, tempMin: Double) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                SimpleDateFormat(FORMAT_DATE, Locale.ROOT).format(Date(date * 1000L)) +
                        "\n" + requireContext().getString(
                    R.string.weather_day,
                    tempDay.toString()
                ) +
                        "\n" + requireContext().getString(
                    R.string.temp_max,
                    tempMax.toString()
                ) +
                        "\n" + requireContext().getString(
                    R.string.temp_min,
                    tempMin.toString()
                )
            )
            type = TYPE_INTENT
        }
        requireContext().startActivity(intent)
    }
}