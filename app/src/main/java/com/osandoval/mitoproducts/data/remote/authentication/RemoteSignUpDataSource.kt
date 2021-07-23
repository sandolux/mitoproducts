package com.osandoval.mitoproducts.data.remote.authentication

import android.util.Log
import com.osandoval.mitoproducts.data.model.User
import com.osandoval.mitoproducts.data.model.UserResponse
import com.osandoval.mitoproducts.data.model.UserSignUp
import com.osandoval.mitoproducts.data.remote.IWebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteSignUpDataSource(private val source: IWebService) {
    suspend fun signUp(user: UserSignUp) : UserResponse {
        var result: UserResponse
        Log.d("meh", "signUp: $user")
        withContext(Dispatchers.IO){
            result = source.signUp(user)
        }

        return result
    }
}