package com.osandoval.mitoproducts.domain.order

import com.osandoval.mitoproducts.data.local.order.LocalOrderDataSource
import com.osandoval.mitoproducts.data.model.OrderEntity
import com.osandoval.mitoproducts.data.model.ProductEntity

class OrderRepository(private val source: LocalOrderDataSource ) : IOrderRepository {
    override suspend fun getOrders(): List<OrderEntity> = source.getOrders()
    override suspend fun getOrdersDetail(uid: String): List<ProductEntity> = source.getOrdersDetail(uid)
}