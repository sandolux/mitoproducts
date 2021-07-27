package com.osandoval.mitoproducts.utils.sharedpreferences

import android.content.Context
import android.util.Log

const val APP_PRODUCTS = "appProducts"
const val KEY_USERNAME = "username"

class SharedPreferences(private val context: Context) : ISharedPreferences {
    override suspend fun saveUser(username: String) {
        val preferences = context.getSharedPreferences(APP_PRODUCTS, Context.MODE_PRIVATE).edit()
        preferences.putString(KEY_USERNAME, username)
        preferences.apply()
    }

    override suspend fun getUser(): String? {
        val preferences = context.getSharedPreferences(APP_PRODUCTS, Context.MODE_PRIVATE)
        return preferences.getString(KEY_USERNAME, "")
    }

    override fun wipeData() {
        val preferences = context.getSharedPreferences(APP_PRODUCTS, Context.MODE_PRIVATE).edit()
        preferences.clear()
        preferences.apply()
    }
}