package com.osandoval.mitoproducts.domain.authentication.signUp

import com.osandoval.mitoproducts.data.model.UserEntity
import com.osandoval.mitoproducts.data.model.UserResponse
import com.osandoval.mitoproducts.data.model.UserSignUp

interface ISignUpRepository {
    suspend fun signUp(user: UserSignUp) : UserResponse
    suspend fun saveUser(user: UserEntity)
}