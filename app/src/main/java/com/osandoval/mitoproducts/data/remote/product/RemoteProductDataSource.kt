package com.osandoval.mitoproducts.data.remote.product

import com.osandoval.mitoproducts.data.model.Product
import com.osandoval.mitoproducts.data.remote.IWebService

class RemoteProductDataSource(private val source: IWebService) {
    suspend fun fetchProducts() : List<Product?> =  source.fetchProducts().data
}