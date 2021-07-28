package com.osandoval.mitoproducts.utils.sharedpreferences

import android.content.Context
import android.util.Log
import com.osandoval.mitoproducts.data.model.UserSession

const val APP_PRODUCTS  =   "appProducts"
const val KEY_USERUID   =   "userUID"
const val KEY_USERNAME  =   "username"
const val KEY_FULLNAME  =   "fullName"
const val KEY_EMAIL     =   "email"

class SharedPreferences(private val context: Context) : ISharedPreferences {
    override suspend fun saveUser(user: UserSession) {
        val preferences = context.getSharedPreferences(APP_PRODUCTS, Context.MODE_PRIVATE).edit()
        preferences.putString(KEY_USERUID, user.uid)
        preferences.putString(KEY_USERNAME, user.user)
        preferences.putString(KEY_FULLNAME, user.fullName)
        preferences.putString(KEY_EMAIL, user.email)

        preferences.apply()
    }

    override suspend fun getUser(): UserSession? {
        val preferences = context.getSharedPreferences(APP_PRODUCTS, Context.MODE_PRIVATE)
        return UserSession(
                preferences.getString(KEY_USERUID, ""),
                preferences.getString(KEY_USERNAME, ""),
                preferences.getString(KEY_FULLNAME, ""),
                preferences.getString(KEY_EMAIL,"")
        )
    }


    override fun wipeData() {
        val preferences = context.getSharedPreferences(APP_PRODUCTS, Context.MODE_PRIVATE).edit()
        preferences.clear()
        preferences.apply()
    }
}