package com.osandoval.mitoproducts.data.local.authentication.login

import com.osandoval.mitoproducts.data.model.UserEntity

class LocalLoginDataSource(private val source: ILoginDao)  {
      suspend fun findUser(username: String, password: String) : UserEntity = source.findUser(username,password)
}