package com.example.weathermediasoftkozyrev.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
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

fun showSnackBar(view: View, message:String){
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
}