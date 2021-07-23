package com.osandoval.mitoproducts.domain.product

import com.osandoval.mitoproducts.data.local.product.LocalProductDataSource
import com.osandoval.mitoproducts.data.model.ProductEntity
import com.osandoval.mitoproducts.data.model.ShoppingCartEntity
import com.osandoval.mitoproducts.data.model.toProductEntity
import com.osandoval.mitoproducts.data.remote.product.RemoteProductDataSource

class ProductRepository(private val remoteDataSource: RemoteProductDataSource,
                        private val localDataSource: LocalProductDataSource
) : IProductRepository {

    override suspend fun fetchProducts(): List<ProductEntity> {
        remoteDataSource.fetchProducts().forEach{ product->
            if (product != null) {
                localDataSource.saveProduct(product.toProductEntity())
            }
        }

        return localDataSource.getProducts()
    }

    override suspend fun getProduct(id: Int): ProductEntity = localDataSource.getProduct(id)

    override suspend fun addShoppingCart(product: ShoppingCartEntity) = localDataSource.addShoppingCart(product)

}