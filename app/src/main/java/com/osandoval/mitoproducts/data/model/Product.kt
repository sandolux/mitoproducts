package com.osandoval.mitoproducts.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey  val id: Int=0,
    val name: String="",
    val description: String="",
    val price: Long=0L,
    val urlImage: String="",
    val status:Boolean=false
)
