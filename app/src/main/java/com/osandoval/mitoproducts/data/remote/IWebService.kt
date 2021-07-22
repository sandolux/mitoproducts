package com.osandoval.mitoproducts.data.remote

import com.osandoval.mitoproducts.data.model.Response
import com.osandoval.mitoproducts.data.model.UserResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface IWebService {
    @GET("product")
    suspend fun fetchProducts(): Response

    @POST("user/validate")
    @FormUrlEncoded
    suspend fun validateUser(@Field("username") username: String,
                             @Field("password") password: String) : UserResponse
}
