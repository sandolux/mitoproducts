package com.osandoval.mitoproducts.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Product(
    val id: Int=0,
    val name: String="",
    val description: String="",
    val price: Long=0L,
    val urlImage: String="",
    val status:Boolean=false
)


@Entity
data class ProductEntity(
    @PrimaryKey  val id: Int=0,
    @ColumnInfo(name="name") val name: String="",
    @ColumnInfo(name="description") val description: String="",
    @ColumnInfo(name="price") val price: Long=0L,
    @ColumnInfo(name="url_image") val urlImage: String="",
    @ColumnInfo(name="status") val status:Boolean=false
)