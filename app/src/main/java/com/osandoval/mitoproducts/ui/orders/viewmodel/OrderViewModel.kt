package com.osandoval.mitoproducts.ui.orders.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.osandoval.mitoproducts.core.Resource
import com.osandoval.mitoproducts.domain.order.IOrderRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class OrderViewModel(private val repository: IOrderRepository) : ViewModel() {
    fun getOrders() = liveData(viewModelScope.coroutineContext + Dispatchers.Main){
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repository.getOrders()))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
}

class OrderViewModelFactory(private val repository: IOrderRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IOrderRepository::class.java).newInstance(repository)
    }

}