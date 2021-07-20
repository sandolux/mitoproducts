package com.osandoval.mitoproducts.domain.shoppingcart

import com.osandoval.mitoproducts.data.model.OrderDetailEntity
import com.osandoval.mitoproducts.data.model.ShoppingCartEntity

interface IShoppingCartRepository {
    suspend fun getShoppingCart() : List<ShoppingCartEntity>
    suspend fun deleteItem(uid: Int)
    suspend fun wipeShoppingCart()
    suspend fun insertOrders(orders: List<OrderDetailEntity>, orderUid:String)
}
