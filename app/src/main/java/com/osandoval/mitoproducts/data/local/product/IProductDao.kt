package com.osandoval.mitoproducts.data.local.product

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.osandoval.mitoproducts.data.model.ProductEntity
import com.osandoval.mitoproducts.data.model.ShoppingCartEntity

@Dao
interface IProductDao {
    @Query("SELECT * FROM ProductEntity")
    suspend fun getProducts(): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE id= :id AND status=1")
    suspend fun getProduct(id : Int): ProductEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProduct(product: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShoppingCart(shoppingCart: ShoppingCartEntity)


}