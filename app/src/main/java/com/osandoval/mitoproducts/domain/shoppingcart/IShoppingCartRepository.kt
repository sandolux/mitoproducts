package com.osandoval.mitoproducts.domain.shoppingcart

import com.osandoval.mitoproducts.data.model.OrderDetailEntity
import com.osandoval.mitoproducts.data.model.ShoppingCartEntity

interface IShoppingCartRepository {
    suspend fun getShoppingCart(userUID: Long) : MutableList<ShoppingCartEntity>
    suspend fun deleteItem(uid: Int)
    suspend fun wipeShoppingCart(userUID: Long)
    suspend fun insertOrders(orders: List<OrderDetailEntity>, orderUid:String, userUID: Long)
}
