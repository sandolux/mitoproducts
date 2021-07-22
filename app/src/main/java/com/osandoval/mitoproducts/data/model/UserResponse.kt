package com.osandoval.mitoproducts.data.model

data class UserResponse (
    val data: User?=null,
    val status: Boolean=false,
    val message: String=""
)

data class User (
    val id: Long,
    val name: String,
    val lastName: String,
    val user: String,
    val password: String,
    val email: String,
    val address: String,
    val phone: String
)
