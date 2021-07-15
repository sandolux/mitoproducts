package com.osandoval.mitoproducts.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Product(
    val id: Int=0,
    val name: String="",
    val description: String="",
    val price: Float=0F,
    val urlImage: String="",
    val status:Boolean=false
)

@Entity
data class ProductEntity(
    @PrimaryKey  val id: Int=0,
    @ColumnInfo(name="name") val name: String="",
    @ColumnInfo(name="description") val description: String="",
    @ColumnInfo(name="price") val price: Float=0F,
    @ColumnInfo(name="url_image") val urlImage: String="",
    @ColumnInfo(name="status") val status:Boolean=false
)

fun Product.toProductEntity(): ProductEntity = ProductEntity(
    this.id, this.name, this.description, this.price, this.urlImage, this.status
)

fun ProductEntity.toShoppingCartEntity(): ShoppingCartEntity = ShoppingCartEntity(
    0, this.id, this.name, this.description, this.price, this.urlImage, this.status
)
