package com.example.weathermediasoftkozyrev

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.work.*
import com.example.weathermediasoftkozyrev.databinding.ActivityMainBinding
import com.example.weathermediasoftkozyrev.db.WeatherDatabase
import com.example.weathermediasoftkozyrev.worker.WeatherNotifyWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(){


    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        simpleWork()
    }

    private fun simpleWork() {
        val mRequest: WorkRequest =
            PeriodicWorkRequestBuilder<WeatherNotifyWorker>(60, TimeUnit.MINUTES).build()
        WorkManager.getInstance(this).enqueue(mRequest)
    }
}
