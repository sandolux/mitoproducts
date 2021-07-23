package com.osandoval.mitoproducts.data.model

data class UserResponse (
    val data: User?=null,
    val status: Boolean=false,
    val message: String=""
)

data class User (
    val id: Long?=0,
    val user: String,
    val name: String,
    val lastName: String,
    val address: String,
    val phone: String,
    val email: String,
    val password: String,
)

data class UserSignUp (
    val id: Long?=0,
    val username: String,
    val name: String,
    val lastName: String,
    val address: String,
    val phone: String,
    val email: String,
    val password: String,
)
