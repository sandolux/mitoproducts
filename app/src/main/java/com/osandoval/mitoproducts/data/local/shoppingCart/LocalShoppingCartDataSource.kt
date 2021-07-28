package com.osandoval.mitoproducts.data.local.shoppingCart

import com.osandoval.mitoproducts.data.model.OrderDetailEntity
import com.osandoval.mitoproducts.data.model.OrderEntity
import com.osandoval.mitoproducts.data.model.ShoppingCartEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalShoppingCartDataSource(private val source: IShoppingCartDao) {
    suspend fun getShoppingCart(userUID: Long) : MutableList<ShoppingCartEntity> {
        var result: MutableList<ShoppingCartEntity>
        withContext(Dispatchers.IO){
            result = source.getShoppingCart(userUID)
        }
        return result
    }

    suspend fun deleteItem(uid: Int) = source.deleteItem(uid)
    suspend fun wipeShoppingCart(userUID: Long) = source.wipeShoppingCart(userUID)

    suspend fun insertOrders(orders : List<OrderDetailEntity>, orderUid: String, userUID: Long) {
        withContext(Dispatchers.IO){
            source.createOrder(OrderEntity(orderUid,userUID))
            source.insertOrders(orders)
        }

    }

}

