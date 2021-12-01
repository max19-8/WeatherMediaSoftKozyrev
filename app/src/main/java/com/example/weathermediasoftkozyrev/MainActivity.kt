package com.example.weathermediasoftkozyrev

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.weathermediasoftkozyrev.databinding.ActivityMainBinding
import com.google.gson.JsonArray
import org.json.JSONArray
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val arr = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun showProgressBar(){
        binding.mainProgressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar(){
        binding.mainProgressBar.visibility = View.INVISIBLE
    }

}