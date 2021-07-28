package com.osandoval.mitoproducts.data.local.order

import com.osandoval.mitoproducts.data.model.OrderEntity
import com.osandoval.mitoproducts.data.model.ProductEntity

class LocalOrderDataSource(private val source: IOrderDao) {
    suspend fun getOrders(userUID: Long) : List<OrderEntity> = source.getOrders(userUID)
    suspend fun getOrdersDetail(uid : String) : List<ProductEntity> = source.getOrdersDetail(uid)
}