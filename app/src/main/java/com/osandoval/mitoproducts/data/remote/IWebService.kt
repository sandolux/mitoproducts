package com.osandoval.mitoproducts.data.remote

import com.osandoval.mitoproducts.data.model.Response
import retrofit2.http.GET

interface IWebService {
    @GET("product")
    suspend fun fetchProducts(): Response
}
