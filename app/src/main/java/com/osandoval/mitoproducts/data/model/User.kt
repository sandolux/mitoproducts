package com.osandoval.mitoproducts.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class UserResponse (
    val data: User?=null,
    val status: Boolean=false,
    val message: String=""
)

data class User (
    val id: Long=0,
    val user: String,
    val name: String,
    val lastName: String,
    val address: String,
    val phone: String,
    val email: String,
    val password: String,
)

data class UserSignUp (
    val id: Long=0,
    val username: String,
    val name: String,
    val lastName: String,
    val address: String,
    val phone: String,
    val email: String,
    val password: String,
)

@Entity
data class UserEntity (
    @PrimaryKey
    @ColumnInfo(name="id") val id: Long=0,
    @ColumnInfo(name="name") val username: String,
    @ColumnInfo(name="lastname") val name: String,
    @ColumnInfo(name="user") val lastName: String,
    @ColumnInfo(name="password") val address: String,
    @ColumnInfo(name="email") val phone: String,
    @ColumnInfo(name="address") val email: String,
    @ColumnInfo(name="phone") val password: String
)

fun UserSignUp.toUserEntity() : UserEntity {
    return UserEntity(this.id, this.name, this.username, this.lastName, this.address, this.phone, this.email, this.password)
}
