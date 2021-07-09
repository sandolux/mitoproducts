package com.osandoval.mitoproducts.data.remote

import com.osandoval.mitoproducts.data.model.Product

interface IWebService {
    suspend fun fetchProducts(): List<Product>
}
