package com.osandoval.mitoproducts.data.local.authentication.login

import androidx.room.Dao
import androidx.room.Query
import com.osandoval.mitoproducts.data.model.UserEntity

@Dao
interface ILoginDao {
    @Query("SELECT * FROM userEntity WHERE user = :username AND password = :password")
    suspend fun findUser(username: String, password: String) : UserEntity
}