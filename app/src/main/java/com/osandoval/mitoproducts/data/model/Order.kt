package com.osandoval.mitoproducts.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OrderEntity(
    @PrimaryKey
    @ColumnInfo(name="uid" ) val uid: String="",

)

//@Entity(primaryKeys = ["orderUid","productUid"])
@Entity
data class OrderDetailEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="uid") val uid: Int=0,
    @ColumnInfo (name="orderUid") val order_uid: String="",
    @ColumnInfo (name="productUid") val product_uid: Int = 0
)

