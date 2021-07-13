package com.osandoval.mitoproducts.domain.product

import android.util.Log
import com.osandoval.mitoproducts.data.local.LocalProductDataSource
import com.osandoval.mitoproducts.data.model.ProductEntity
import com.osandoval.mitoproducts.data.model.toProductEntity
import com.osandoval.mitoproducts.data.remote.RemoteProductDataSource

class ProductRepository(private val remoteDataSource: RemoteProductDataSource,
                        private val localDataSource: LocalProductDataSource) : IProductRepository {

    override suspend fun fetchProducts(): List<ProductEntity> {
        remoteDataSource.fetchProducts().forEach{ product->
            if (product != null) {
                localDataSource.saveProduct(product.toProductEntity())
            }
        }

        return localDataSource.getProducts()
    }
}