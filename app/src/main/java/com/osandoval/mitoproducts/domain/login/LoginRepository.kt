package com.osandoval.mitoproducts.domain.login

import com.osandoval.mitoproducts.data.model.UserResponse
import com.osandoval.mitoproducts.data.remote.RemoteLoginDataSource

class LoginRepository(private val remote: RemoteLoginDataSource) : ILoginRepository {
    override suspend fun validateUser(username: String, password: String): UserResponse = remote.validateUser(username, password)
}