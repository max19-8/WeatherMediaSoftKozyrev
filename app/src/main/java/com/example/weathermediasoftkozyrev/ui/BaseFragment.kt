package com.example.weathermediasoftkozyrev.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<vBinding : ViewBinding> : Fragment(){

    lateinit  var binding: vBinding
    protected abstract fun getViewBinding(): vBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        init()
        return binding.root
    }

    private fun init() {
        binding = getViewBinding()
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}