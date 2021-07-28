package com.osandoval.mitoproducts.utils.sharedpreferences

import com.osandoval.mitoproducts.data.model.UserSession

interface ISharedPreferences {
    suspend fun saveUser(user: UserSession)
    suspend fun getUser() : UserSession?
    fun wipeData()
}