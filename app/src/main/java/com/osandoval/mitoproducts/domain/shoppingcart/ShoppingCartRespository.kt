package com.osandoval.mitoproducts.domain.shoppingcart

import com.osandoval.mitoproducts.data.local.shoppingCart.LocalShoppingCartDataSource
import com.osandoval.mitoproducts.data.model.OrderDetailEntity
import com.osandoval.mitoproducts.data.model.ShoppingCartEntity

class ShoppingCartRepository(private val localDataSource: LocalShoppingCartDataSource) : IShoppingCartRepository {

    override suspend fun getShoppingCart(): MutableList<ShoppingCartEntity> = localDataSource.getShoppingCart()
    override suspend fun deleteItem(uid: Int) = localDataSource.deleteItem(uid)
    override suspend fun wipeShoppingCart() = localDataSource.wipeShoppingCart()
    override suspend fun insertOrders(orders: List<OrderDetailEntity>, orderUid: String) = localDataSource.insertOrders(orders, orderUid)
}