package com.technolearn.rasoulonlineshop.util

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.technolearn.rasoulonlineshop.BuildConfig
import com.technolearn.rasoulonlineshop.R
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat
import timber.log.Timber

object Extensions {

    fun Int.dp() = (this * Resources.getSystem().displayMetrics.density).toInt()
    fun Float.dp() = (this * Resources.getSystem().displayMetrics.density)

    fun Int?.orDefault() = this ?: 0
    fun Float?.orDefault() = this ?: 0f
    fun Double?.orDefault() = this ?: 0.0
    fun Long?.orDefault() = this ?: 0L
    fun String?.orDefault() = this ?: ""
    fun String?.orUnknown() = if (this.isNullOrEmpty()) {
        "نامشخص"
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
    fun getHumanReadableDate(date: Long?): String {
        if (date != null) {
            val persianDateFormat = PersianDateFormat("j F Y")
            return persianDateFormat.format(PersianDate(date))
        }

        return ""
    }

    fun Long.toHumanReadableDate(): String {
        return try {
            val persianDateFormat = PersianDateFormat("j F Y")
            persianDateFormat.format(PersianDate(this))
        } catch (ex: Exception) {
            ""
        }
    }
    fun Context.isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    fun View.openKeyboard() {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    fun View.visible() {
        this.visibility = View.VISIBLE
    }

    fun View.invisible() {
        this.visibility = View.INVISIBLE
    }

    fun View.gone() {
        this.visibility = View.GONE
    }

    fun View.visibility(visible: Boolean) {
        if (visible) {
            this.visibility = View.VISIBLE
        } else {
            this.visibility = View.GONE
        }
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