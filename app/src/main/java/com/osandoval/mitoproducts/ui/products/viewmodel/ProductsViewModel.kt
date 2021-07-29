package com.osandoval.mitoproducts.ui.products.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.osandoval.mitoproducts.core.Resource
import com.osandoval.mitoproducts.domain.product.ProductRepository
import kotlinx.coroutines.Dispatchers

class ProductsViewModel(private val repository: ProductRepository) : ViewModel() {
    fun getProducts() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repository.fetchProducts()))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}

class ProductViewModelFactory(private val repository: ProductRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return modelClass.getConstructor(ProductRepository::class.java).newInstance(repository)
    }
}