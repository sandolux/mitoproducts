package com.osandoval.mitoproducts.domain.authentication.login

import com.osandoval.mitoproducts.data.local.authentication.login.LocalLoginDataSource
import com.osandoval.mitoproducts.data.model.UserEntity
import com.osandoval.mitoproducts.data.model.UserResponse
import com.osandoval.mitoproducts.data.remote.authentication.RemoteLoginDataSource

class LoginRepository(private val local: LocalLoginDataSource, private val remote: RemoteLoginDataSource) : ILoginRepository {
    override suspend fun validateUser(username: String, password: String): UserResponse = remote.validateUser(username, password)
    override suspend fun findUser(username: String, password: String): UserEntity = local.findUser(username, password)
}