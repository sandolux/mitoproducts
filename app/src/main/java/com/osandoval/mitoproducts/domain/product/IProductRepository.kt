package com.osandoval.mitoproducts.domain.product

import com.osandoval.mitoproducts.data.model.ProductEntity
import com.osandoval.mitoproducts.data.model.ShoppingCartEntity

interface IProductRepository {
    suspend fun fetchProducts() : List<ProductEntity>
    suspend fun getProduct(id: Int) : ProductEntity
    suspend fun addShoppingCart(product : ShoppingCartEntity)
}