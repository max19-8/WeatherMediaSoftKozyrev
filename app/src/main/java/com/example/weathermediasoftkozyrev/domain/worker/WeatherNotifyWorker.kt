package com.example.weathermediasoftkozyrev.domain.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.weathermediasoftkozyrev.presentation.ui.MainActivity
import com.example.weathermediasoftkozyrev.R
import com.example.weathermediasoftkozyrev.data.repository.notificationrepository.NotifyRepositoryImpl
import com.example.weathermediasoftkozyrev.domain.utils.getCurrentDateTime
import com.example.weathermediasoftkozyrev.domain.utils.loadImageFromURL
import com.example.weathermediasoftkozyrev.domain.utils.toString

class WeatherNotifyWorker(val context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    private lateinit var preferences: SharedPreferences

    companion object {
        const val CHANNEL_ID = "channel_id"
        const val NOTIFICATION_ID = 1
        const val CHANNEL_NAME = "channel weather notification"
        const val CHANNEL_DESCRIPTION = "channel description"
    }

    override suspend fun doWork(): Result {
        preferences = context.getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE)
        val lat = preferences.getFloat("LAT", 54.333332F)
        val lon = preferences.getFloat("LON", 48.400002F)
        val city = preferences.getString("CITY", "Ульяновск")
        val current = NotifyRepositoryImpl().getNotify(lat, lon)
        val temp = context.getString(R.string.temp, current.current.temp.toInt().toString())
        val icon =
            "https://openweathermap.org/img/wn/" + current.current.weather[0].icon + "@2x.png"
        val load = loadImageFromURL(icon, name = null)
        val desc = current.current.weather[0].description
        val date = getCurrentDateTime()
        val dateInString = date.toString("HH:mm")
        showNotification(city, temp, load, desc, dateInString)
        return Result.success()
    }

    private fun showNotification(
        city: String?, temp: String, largeIcon: Bitmap?, desc: String, time: String
    ) {
        val notificationLayout =
            RemoteViews(context.packageName, R.layout.notification_layout).apply {
                setImageViewBitmap(R.id.image_view_notify_icon, largeIcon)
                setTextViewText(R.id.text_view_notify_city, city)
                setTextViewText(R.id.text_view_notify_description, desc)
                setTextViewText(R.id.text_view_notify_temp, temp)
                setTextViewText(R.id.text_view_notify_time, time)
            }
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent =
            PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setCustomContentView(notificationLayout)
            .setContent(notificationLayout)
            .setSmallIcon(R.drawable.status_bar_cloud)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = CHANNEL_DESCRIPTION
            }
            val notificationManager = applicationContext.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        with(NotificationManagerCompat.from(applicationContext)) {
            notify(NOTIFICATION_ID, notification.build())
        }
    }
}