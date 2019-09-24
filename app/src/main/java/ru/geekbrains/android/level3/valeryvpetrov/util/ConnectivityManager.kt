package ru.geekbrains.android.level3.valeryvpetrov.util

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager

class ConnectivityManager(
    private val application: Application?
) {

    fun isNetworkConnected(): Boolean {
        checkNotNull(application)

        val connectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}