package com.osandoval.mitoproducts.domain.shoppingcart

import com.osandoval.mitoproducts.data.local.LocalShoppingCartDataSource
import com.osandoval.mitoproducts.data.model.ShoppingCartEntity

class ShoppingCartRepository(private val localDataSource: LocalShoppingCartDataSource) : IShoppingCartRepository {

    override suspend fun getShoppingCart(): MutableList<ShoppingCartEntity> = localDataSource.getShoppingCart()
    override suspend fun deleteItem(uid: Int) = localDataSource.deleteItem(uid)
    override suspend fun wipeShoppingCart() = localDataSource.wipeShoppingCart()

}