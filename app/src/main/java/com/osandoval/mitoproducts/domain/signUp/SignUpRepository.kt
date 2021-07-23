package com.osandoval.mitoproducts.domain.signUp

import com.osandoval.mitoproducts.data.model.UserResponse
import com.osandoval.mitoproducts.data.model.UserSignUp
import com.osandoval.mitoproducts.data.remote.authentication.RemoteSignUpDataSource

class SignUpRepository(private val remote: RemoteSignUpDataSource) : ISignUpRepository {
    override suspend fun signUp(user: UserSignUp): UserResponse = remote.signUp(user)

}