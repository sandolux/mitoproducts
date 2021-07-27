package com.osandoval.mitoproducts.utils.sharedpreferences

interface ISharedPreferences {
    suspend fun saveUser(username: String)
    suspend fun getUser() : String?
    suspend fun wipeData()
}