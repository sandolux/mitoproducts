package com.osandoval.mitoproducts.data.local

import com.osandoval.mitoproducts.data.model.ProductEntity

class LocalProductDataSource(private val source: IProductDao) {
    suspend fun getProducts() : List<ProductEntity> = source.getProducts()
    suspend fun saveProduct(product: ProductEntity) = source.saveProduct(product)
}