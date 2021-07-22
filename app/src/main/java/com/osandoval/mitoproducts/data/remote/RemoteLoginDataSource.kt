package com.osandoval.mitoproducts.data.remote

import android.util.Log
import com.osandoval.mitoproducts.data.model.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteLoginDataSource(private val source: IWebService) {
    suspend fun validateUser(username: String, password: String) : UserResponse {
        var result: UserResponse

        withContext(Dispatchers.IO){
            result = source.validateUser(username, password)
        }

        return result
    }
}