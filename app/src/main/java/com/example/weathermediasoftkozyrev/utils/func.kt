package com.example.weathermediasoftkozyrev.utils
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.icu.util.MeasureUnit.KELVIN
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import java.io.InputStream
import java.lang.Exception
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

//drag and drop(recyclerView) ListCityFragment
val itemTouchHelper = object : ItemTouchHelper.Callback(){
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        return makeMovementFlags(dragFlags,0)
    }
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        recyclerView.adapter?.notifyItemMoved(viewHolder.adapterPosition,
            target.adapterPosition)
        return true
    }
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }
}

// load icon for notification
fun loadImageFromURL(url: String?, name: String?): Bitmap? {
    return try {
        val `is` = URL(url).content as InputStream
        Drawable.createFromStream(`is`, name).toBitmap(100,100)
    } catch (e: Exception) {
        null
    }
}

//format date for notification
fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}
fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}

//check internet connection


fun isNetworkAvailable(context: Context): Boolean {
    // It answers the queries about the state of network connectivity.
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val activeNetWork = connectivityManager.getNetworkCapabilities(network) ?: return false
    return when {
        activeNetWork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetWork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        activeNetWork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}



/**
 * Get formatted local time for the sunrise/sunset
 */
fun Int.getTime(): String? {
    val date = Date(this * 1000L)
    val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault()
    return sdf.format(date)
}