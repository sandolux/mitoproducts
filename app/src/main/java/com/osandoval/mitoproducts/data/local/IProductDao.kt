package com.osandoval.mitoproducts.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.osandoval.mitoproducts.data.model.ProductEntity

@Dao
interface IProductDao {
    @Query("SELECT * FROM ProductEntity")
    suspend fun getProducts(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProduct(product: ProductEntity)
}