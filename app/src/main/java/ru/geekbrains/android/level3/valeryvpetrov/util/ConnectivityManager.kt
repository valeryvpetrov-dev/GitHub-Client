package ru.geekbrains.android.level3.valeryvpetrov.util

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

class ConnectivityManager
@Inject constructor(
    private val context: Context
) {

    fun isNetworkConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}