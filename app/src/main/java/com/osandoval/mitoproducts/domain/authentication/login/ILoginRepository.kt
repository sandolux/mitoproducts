package com.osandoval.mitoproducts.domain.authentication.login

import com.osandoval.mitoproducts.data.model.UserEntity
import com.osandoval.mitoproducts.data.model.UserResponse

interface ILoginRepository {
    suspend fun validateUser(username: String, password: String) : UserResponse
    suspend fun findUser(username: String, password: String) : UserEntity
}