package com.example.twgproducttask.facades

import android.content.SharedPreferences
import java.util.*


/* This class is used to save and retrieve the user id.
        */
class SharedPreferencesFacade(private val sharedPreferences: SharedPreferences) {

    private val userKey = "UserId"


    fun saveUserId(userId: String) {
        println(" twgtask userId "+userId)
        sharedPreferences.edit().putString(userKey, userId).apply()
    }

    fun getUserId(): String? {
        return sharedPreferences.getString(userKey, null)
    }


    private fun containsKey(key: String): Boolean = sharedPreferences.contains(key)

    private fun getString(key: String, defaultValue: String? = null): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    private fun putString(key: String, string: String) {
        sharedPreferences.edit().putString(key, string).apply()
    }
}
