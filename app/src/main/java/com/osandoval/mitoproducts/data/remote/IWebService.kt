package com.osandoval.mitoproducts.data.remote

import com.osandoval.mitoproducts.data.model.Product
import retrofit2.http.GET

interface IWebService {
    @GET("product")
    suspend fun fetchProducts(): List<Product>
}
