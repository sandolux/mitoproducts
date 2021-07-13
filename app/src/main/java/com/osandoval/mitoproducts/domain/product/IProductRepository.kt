package com.osandoval.mitoproducts.domain.product

import com.osandoval.mitoproducts.data.model.Product
import com.osandoval.mitoproducts.data.model.ProductEntity

interface IProductRepository {
    suspend fun fetchProducts() : List<ProductEntity>
}