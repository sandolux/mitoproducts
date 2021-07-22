package com.osandoval.mitoproducts.domain.login

import com.osandoval.mitoproducts.data.model.UserResponse

interface ILoginRepository {
    suspend fun validateUser(username: String, password: String) : UserResponse

}