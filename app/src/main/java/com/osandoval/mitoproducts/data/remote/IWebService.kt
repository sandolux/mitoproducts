package com.osandoval.mitoproducts.data.remote

import com.osandoval.mitoproducts.data.model.Response
import com.osandoval.mitoproducts.data.model.User
import com.osandoval.mitoproducts.data.model.UserResponse
import com.osandoval.mitoproducts.data.model.UserSignUp
import retrofit2.http.*

interface IWebService {
    @GET("product")
    suspend fun fetchProducts(): Response

    @POST("user/validate")
    @FormUrlEncoded
    suspend fun validateUser(@Field("username") username: String,
                             @Field("password") password: String) : UserResponse

    @POST("user")
    suspend fun signUp(@Body request: UserSignUp) : UserResponse
}
