package com.osandoval.mitoproducts.ui.products.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.osandoval.mitoproducts.core.Resource
import com.osandoval.mitoproducts.data.model.toShoppingCartEntity
import com.osandoval.mitoproducts.domain.product.ProductRepository
import com.osandoval.mitoproducts.utils.sharedpreferences.SharedPreferences
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class ProductDetailViewModel(private val repository: ProductRepository, private val sharedPreferences: SharedPreferences) : ViewModel() {
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
            val userUID = sharedPreferences.getUser()?.uid!!.toLong()
            val product = repository.getProduct(id).toShoppingCartEntity(userUID)
            emit(Resource.Success(repository.addShoppingCart(product)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}

class ProductDetailViewModelFactory(private val repository: ProductRepository, private val sharedPreferences: SharedPreferences)
    : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            ProductRepository::class.java,
            SharedPreferences::class.java
        ).newInstance(repository,sharedPreferences)
    }
}