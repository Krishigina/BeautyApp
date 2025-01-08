package com.example.beautyapp.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPrefsManager {
    private const val PREF_NAME = "AppSettings"
    private const val KEY_IS_USER_AUTHORIZED = "isUserAuthorized"
    private const val KEY_USER_ID = "userId"

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setUserAuthorized(isAuthorized: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_IS_USER_AUTHORIZED, isAuthorized).apply()
    }

    fun isUserAuthorized(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_USER_AUTHORIZED, false)
    }

    fun setUserId(userId: String) {
        sharedPreferences.edit().putString(KEY_USER_ID, userId).apply()
    }

    fun getUserId(): String? {
        return sharedPreferences.getString(KEY_USER_ID, null)
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}