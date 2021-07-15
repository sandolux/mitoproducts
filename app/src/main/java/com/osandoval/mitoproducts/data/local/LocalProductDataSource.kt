package com.osandoval.mitoproducts.data.local

import com.osandoval.mitoproducts.data.model.ProductEntity
import com.osandoval.mitoproducts.data.model.ShoppingCartEntity

class LocalProductDataSource(private val source: IProductDao) {
    suspend fun getProduct(id : Int) : ProductEntity = source.getProduct(id)
    suspend fun getProducts() : List<ProductEntity> = source.getProducts()
    suspend fun saveProduct(product: ProductEntity) = source.saveProduct(product)
    suspend fun addShoppingCart(shoppingCart: ShoppingCartEntity) = source.addShoppingCart(shoppingCart)
}