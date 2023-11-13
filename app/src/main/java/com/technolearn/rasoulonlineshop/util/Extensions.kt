package com.technolearn.rasoulonlineshop.util

import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.widget.Toast
import com.technolearn.rasoulonlineshop.BuildConfig
import com.technolearn.rasoulonlineshop.R
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Extensions {

    fun Int.dp() = (this * Resources.getSystem().displayMetrics.density).toInt()
    fun Float.dp() = (this * Resources.getSystem().displayMetrics.density)

    fun Int?.orDefault() = this ?: 0
    fun Float?.orDefault() = this ?: 0f
    fun Double?.orDefault() = this ?: 0.0
    fun Long?.orDefault() = this ?: 0L
    fun String?.orDefault() = this ?: ""
    fun String?.orUnknown() = if (this.isNullOrEmpty()) {
        "Unknown"
    } else {
        this
    }

    fun Boolean?.orFalse() = this ?: false
    fun Boolean?.orTrue() = this ?: true
    fun String?.orNull(): String? {
        return if (this.isNullOrEmpty()) {
            null
        } else {
            this
        }
    }

    fun Int?.isNotNullAndZero(): Boolean {
        return this != null && this != 0
    }

    fun timber(msg: String){
        Timber.d(msg)
    }

    fun Context.toast(msg: String){
        showToast(this, msg)
    }
    fun Long.toHumanReadableDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = Date(this * 1000)
        return sdf.format(date)
    }

    fun Context.isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }


    fun showToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    fun Context.showNetworkConnectionErrorToast() {

        showToast(this, getString(R.string.no_connection_msg))
    }
    private var lastTime = 0L
    private var msgOld = ""
    fun Context.showHttpErrorToast(msg: String? = null) {
        val time = System.currentTimeMillis()
        if ((time-lastTime) > 3 && msgOld != msg){
            if (BuildConfig.DEBUG) {
                showToast(this, msg.toString())

            } else {
                showToast(this, getString(R.string.network_error_msg))
            }
            lastTime = time
            msgOld = msg!!
        }
    }
}