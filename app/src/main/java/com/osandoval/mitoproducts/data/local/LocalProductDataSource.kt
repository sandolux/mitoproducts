package com.osandoval.mitoproducts.data.local

import com.osandoval.mitoproducts.data.model.ProductEntity

class LocalProductDataSource(private val source: IProductDao) {
    suspend fun fetchProducts() : List<ProductEntity> = source.getProducts()
}