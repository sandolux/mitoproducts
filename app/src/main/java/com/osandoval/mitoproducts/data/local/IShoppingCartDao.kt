package com.osandoval.mitoproducts.data.local

import androidx.room.Dao
import androidx.room.Query
import com.osandoval.mitoproducts.data.model.ShoppingCartEntity

@Dao
interface IShoppingCartDao {
    @Query("SELECT * FROM ShoppingCartEntity")
    suspend fun getShoppingCart() : List<ShoppingCartEntity>

    @Query("DELETE FROM ShoppingCartEntity WHERE uuid= :uid")
    suspend fun deleteItem(uid:Int)

    @Query("DELETE FROM ShoppingCartEntity")
    suspend fun wipeShoppingCart()

}