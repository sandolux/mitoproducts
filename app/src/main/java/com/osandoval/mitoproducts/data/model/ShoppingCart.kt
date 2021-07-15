package com.osandoval.mitoproducts.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShoppingCartEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="uuid" ) val uid: Int=0,
    @ColumnInfo(name="id" ) val id: Int=0,
    @ColumnInfo(name="name") val name: String="",
    @ColumnInfo(name="description") val description: String="",
    @ColumnInfo(name="price") val price: Float=0F,
    @ColumnInfo(name="url_image") val urlImage: String="",
    @ColumnInfo(name="status") val status:Boolean=false
)
