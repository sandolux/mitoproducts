package com.osandoval.mitoproducts.domain.order

import com.osandoval.mitoproducts.data.model.OrderEntity
import com.osandoval.mitoproducts.data.model.ProductEntity

interface IOrderRepository {
    suspend fun getOrders(userUID:Long) : List<OrderEntity>
    suspend fun getOrdersDetail(uid : String) : List<ProductEntity>
}