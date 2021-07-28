package com.osandoval.mitoproducts.ui.orders.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.osandoval.mitoproducts.core.Resource
import com.osandoval.mitoproducts.domain.order.OrderRepository
import com.osandoval.mitoproducts.utils.sharedpreferences.SharedPreferences
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class OrderViewModel(private val repository: OrderRepository, private val sharedPreferences: SharedPreferences) : ViewModel() {
    fun getOrders() = liveData(viewModelScope.coroutineContext + Dispatchers.Main){
        emit(Resource.Loading())
        try {
            val userUID = sharedPreferences.getUser()?.uid!!.toLong()
            emit(Resource.Success(repository.getOrders(userUID)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
}

class OrderViewModelFactory(private val repository: OrderRepository, private val sharedPreferences: SharedPreferences)
    : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            OrderRepository::class.java,
            SharedPreferences::class.java
        ).newInstance(repository, sharedPreferences)
    }

}