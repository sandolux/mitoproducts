package com.osandoval.mitoproducts.data.local.authentication.signup

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.osandoval.mitoproducts.data.model.UserEntity
import com.osandoval.mitoproducts.data.model.UserSignUp

@Dao
interface ISignUpDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: UserEntity)
}
