package com.osandoval.mitoproducts.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.osandoval.mitoproducts.data.model.ProductEntity

@Database(entities = [ProductEntity::class], version=1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productDao(): IProductDao

    companion object{
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase{
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "product_table"
            ).build()

            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}