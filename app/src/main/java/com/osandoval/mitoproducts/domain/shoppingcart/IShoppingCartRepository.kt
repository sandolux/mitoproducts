package com.osandoval.mitoproducts.domain.shoppingcart

import com.osandoval.mitoproducts.data.model.ShoppingCartEntity

interface IShoppingCartRepository {
    suspend fun getShoppingCart() : List<ShoppingCartEntity>
    suspend fun deleteItem(uid: Int)
    suspend fun wipeShoppingCart()
}
