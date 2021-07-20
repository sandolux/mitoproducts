package com.osandoval.mitoproducts.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.osandoval.mitoproducts.data.local.order.IOrderDao
import com.osandoval.mitoproducts.data.local.product.IProductDao
import com.osandoval.mitoproducts.data.local.shoppingCart.IShoppingCartDao
import com.osandoval.mitoproducts.data.model.OrderDetailEntity
import com.osandoval.mitoproducts.data.model.OrderEntity
import com.osandoval.mitoproducts.data.model.ProductEntity
import com.osandoval.mitoproducts.data.model.ShoppingCartEntity

@Database(entities = [
                      ProductEntity::class,
                      ShoppingCartEntity::class,
                      OrderEntity::class, OrderDetailEntity::class
                     ],
          version=4,
          exportSchema = false)

abstract class AppDatabase: RoomDatabase() {
    abstract fun productDao(): IProductDao
    abstract fun shoppingCartDao() : IShoppingCartDao
    abstract fun orderDao() : IOrderDao

    companion object{
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase{
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "product_table,shoppingCart_table,order_table,orderDetail_table"
            ).build()

            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}