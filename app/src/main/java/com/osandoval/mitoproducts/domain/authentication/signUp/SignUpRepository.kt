package com.osandoval.mitoproducts.domain.authentication.signUp

import com.osandoval.mitoproducts.data.local.authentication.signup.LocalSignUpDataSource
import com.osandoval.mitoproducts.data.model.UserEntity
import com.osandoval.mitoproducts.data.model.UserResponse
import com.osandoval.mitoproducts.data.model.UserSignUp
import com.osandoval.mitoproducts.data.remote.authentication.RemoteSignUpDataSource

class SignUpRepository(private val local: LocalSignUpDataSource,
                       private val remote: RemoteSignUpDataSource) : ISignUpRepository {

    override suspend fun signUp(user: UserSignUp) : UserResponse = remote.signUp(user)
    override suspend fun saveUser(user: UserEntity) = local.saveUser(user)
}