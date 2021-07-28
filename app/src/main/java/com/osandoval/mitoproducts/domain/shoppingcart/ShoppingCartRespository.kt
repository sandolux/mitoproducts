package com.osandoval.mitoproducts.domain.shoppingcart

import com.osandoval.mitoproducts.data.local.shoppingCart.LocalShoppingCartDataSource
import com.osandoval.mitoproducts.data.model.OrderDetailEntity
import com.osandoval.mitoproducts.data.model.ShoppingCartEntity

class ShoppingCartRepository(private val localDataSource: LocalShoppingCartDataSource) : IShoppingCartRepository {
    override suspend fun getShoppingCart(userUID: Long): MutableList<ShoppingCartEntity> = localDataSource.getShoppingCart(userUID)

    override suspend fun deleteItem(uid: Int) = localDataSource.deleteItem(uid)

    override suspend fun wipeShoppingCart(userUID: Long) = localDataSource.wipeShoppingCart(userUID)

    override suspend fun insertOrders(orders: List<OrderDetailEntity>,
                                      orderUid: String,
                                      userUID: Long
    ) = localDataSource.insertOrders(orders, orderUid,userUID)

}