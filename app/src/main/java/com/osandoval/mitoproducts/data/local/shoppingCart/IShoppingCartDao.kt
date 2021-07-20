package com.osandoval.mitoproducts.data.local.shoppingCart

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.osandoval.mitoproducts.data.model.OrderDetailEntity
import com.osandoval.mitoproducts.data.model.OrderEntity
import com.osandoval.mitoproducts.data.model.ShoppingCartEntity

@Dao
interface IShoppingCartDao {
    @Query("SELECT * FROM ShoppingCartEntity")
    suspend fun getShoppingCart() : MutableList<ShoppingCartEntity>

    //suspend fun getLShoppingCart() : LiveData<MutableList<ShoppingCartEntity>>

    @Query("DELETE FROM ShoppingCartEntity WHERE uid= :uid")
    suspend fun deleteItem(uid:Int)

    @Query("DELETE FROM ShoppingCartEntity")
    suspend fun wipeShoppingCart()

    @Insert
    suspend fun createOrder(orders : OrderEntity)

    @Insert
    suspend fun insertOrders(orders : List<OrderDetailEntity>)
}

/*
* @Insert
     public void insertWithFriends(User user, List<User> friends);
* */