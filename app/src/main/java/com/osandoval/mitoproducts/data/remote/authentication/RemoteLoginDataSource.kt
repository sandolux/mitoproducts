package com.osandoval.mitoproducts.data.remote.authentication

import com.osandoval.mitoproducts.data.model.UserResponse
import com.osandoval.mitoproducts.data.remote.IWebService
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