package com.osandoval.mitoproducts.data.local.order

import androidx.room.Dao
import androidx.room.Query
import com.osandoval.mitoproducts.data.model.OrderEntity
import com.osandoval.mitoproducts.data.model.ProductEntity

@Dao
interface IOrderDao {

    @Query("SELECT * FROM OrderEntity WHERE userUid = :userUID")
    suspend fun getOrders(userUID: Long) : List<OrderEntity>

    @Query(
        """
                SELECT p.id, p.name, p.description, p.price, p.url_image, p.status 
                    FROM ProductEntity p
                    INNER JOIN OrderDetailEntity d ON p.id = d.productUid 
                WHERE d.orderUid LIKE :uid 
                                 AND p.status = 1
           """)
    suspend fun getOrdersDetail(uid : String="") : List<ProductEntity>


}