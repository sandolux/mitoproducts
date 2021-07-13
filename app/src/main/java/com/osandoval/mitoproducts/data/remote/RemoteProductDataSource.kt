package com.osandoval.mitoproducts.data.remote

import com.osandoval.mitoproducts.data.model.Product

class RemoteProductDataSource(private val source: IWebService) {
    suspend fun fetchProducts() : List<Product?> {
        return  source.fetchProducts().data
    }
}