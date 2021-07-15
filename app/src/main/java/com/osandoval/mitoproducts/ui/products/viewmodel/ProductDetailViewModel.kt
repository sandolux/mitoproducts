package com.osandoval.mitoproducts.ui.products.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.osandoval.mitoproducts.core.Resource
import com.osandoval.mitoproducts.data.model.toShoppingCartEntity
import com.osandoval.mitoproducts.domain.product.ProductRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class ProductDetailViewModel(private val repository: ProductRepository) : ViewModel() {
    fun getProduct(id: Int) = liveData(Dispatchers.IO){
        emit(Resource.Loading())
        try{
            emit(Resource.Success(repository.getProduct(id)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun addShoppingCart(id: Int) = liveData(Dispatchers.IO){
        emit(Resource.Loading())
        try {
            val product = repository.getProduct(id).toShoppingCartEntity()
            Log.d("meh", "addShoppingCart: $product")
            emit(Resource.Success(repository.addShoppingCart(product)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}

class ProductDetailViewModelFactory(private val repository: ProductRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ProductRepository::class.java).newInstance(repository)
    }
}