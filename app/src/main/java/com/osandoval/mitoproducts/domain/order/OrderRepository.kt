package com.osandoval.mitoproducts.domain.order

import com.osandoval.mitoproducts.data.local.order.LocalOrderDataSource
import com.osandoval.mitoproducts.data.model.OrderEntity
import com.osandoval.mitoproducts.data.model.ProductEntity

class OrderRepository(private val source: LocalOrderDataSource ) : IOrderRepository {
    override suspend fun getOrders(userUID:Long): List<OrderEntity> = source.getOrders(userUID)
    override suspend fun getOrdersDetail(uid: String): List<ProductEntity> = source.getOrdersDetail(uid)
}