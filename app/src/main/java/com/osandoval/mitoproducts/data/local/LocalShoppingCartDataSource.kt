package com.osandoval.mitoproducts.data.local

import com.osandoval.mitoproducts.data.model.ShoppingCartEntity

class LocalShoppingCartDataSource(private val source: IShoppingCartDao) {
    suspend fun getShoppingCart() : List<ShoppingCartEntity> = source.getShoppingCart()
    suspend fun deleteItem(uid: Int) = source.deleteItem(uid)
    suspend fun wipeShoppingCart() = source.wipeShoppingCart()
}