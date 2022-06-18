package ru.binnyatoff.messenger.ui.screens

import android.content.Context
import javax.inject.Inject

class MainSharedPreferences @Inject constructor(private val context: Context) {
    private val shPref = context.getSharedPreferences("Token", Context.MODE_PRIVATE)

    fun saveToken(value: String) {
        val edit = shPref.edit()
        edit.putString("token", value).apply()
    }

    fun getToken(): String? {
        return shPref.getString("token", "")
    }
}