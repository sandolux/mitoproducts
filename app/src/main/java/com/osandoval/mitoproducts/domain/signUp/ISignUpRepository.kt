package com.osandoval.mitoproducts.domain.signUp

import com.osandoval.mitoproducts.data.model.UserResponse
import com.osandoval.mitoproducts.data.model.UserSignUp

interface ISignUpRepository {
    suspend fun signUp(user: UserSignUp) : UserResponse
}