package ru.geekbrains.android.level3.valeryvpetrov.util

import android.app.Activity

fun Activity.toast(message: String, duration: Int = android.widget.Toast.LENGTH_LONG) {
    android.widget.Toast.makeText(this, message, duration)
        .show()
}