package com.osandoval.mitoproducts.data.local.authentication.signup

import com.osandoval.mitoproducts.data.model.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalSignUpDataSource(private val local: ISignUpDao) {
    suspend fun saveUser(user: UserEntity) {
        withContext(Dispatchers.IO){
            local.saveUser(user)
        }
    }
}